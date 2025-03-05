package com.csee.swplus.mileage.auth.filter;

import javax.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.auth.exception.DoNotLoginException;
import com.csee.swplus.mileage.auth.service.AuthService;
import com.csee.swplus.mileage.auth.util.JwtUtil;
import com.csee.swplus.mileage.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final Key SECRET_KEY;  // Key 타입으로 선언

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/mileage/auth/login$"
    );

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("🚀 JwtTokenFilter: 요청 URI: {}", requestURI);

        if (isExcludedPath(requestURI)) {
            log.debug("🔸 JwtTokenFilter: 제외된 경로입니다. 필터 체인 계속 진행.");
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            log.debug("🍪 JwtTokenFilter: 쿠키 수집, 총 쿠키 개수: {}", cookies.length);
            for (Cookie cookie : cookies) {
                log.debug("🍪 JwtTokenFilter: 쿠키 이름: {}, 값: {}", cookie.getName(), cookie.getValue());
                if ("authToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    log.debug("🔑 JwtTokenFilter: authToken 쿠키 발견: {}", token);
                }
            }
        } else {
            log.debug("⚠️ JwtTokenFilter: 요청에 쿠키가 없습니다.");
        }

        if (token == null) {
            log.error("❌ JwtTokenFilter: authToken 쿠키가 존재하지 않습니다. 로그인 필요.");
            throw new DoNotLoginException();
        }

        String userId = JwtUtil.getUserId(token, SECRET_KEY);
        log.debug("🔍 JwtTokenFilter: 토큰으로부터 추출한 userId: {}", userId);

        Users loginUser = authService.getLoginUser(userId);
        log.debug("👤 JwtTokenFilter: 인증된 사용자: {}", loginUser);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getUniqueId(), null, null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.debug("✅ JwtTokenFilter: SecurityContext에 인증 객체를 저장하였습니다.");

        filterChain.doFilter(request, response);
        log.debug("🏁 JwtTokenFilter: 필터 체인 진행 완료.");
    }

    private boolean isExcludedPath(String requestURI) {
        boolean excluded = EXCLUDED_PATHS.stream().anyMatch(requestURI::matches);
        log.debug("🔎 JwtTokenFilter: {} 경로가 제외 대상인지 여부: {}", requestURI, excluded);
        return excluded;
    }
}