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
    private final Key SECRET_KEY;

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/mileage/auth/login$",
            "/mileage/api/mileage/auth/login$"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("🚀 JwtTokenFilter: 요청 URI: {}", requestURI);

        if (isExcludedPath(requestURI)) {
            log.debug("🔸 JwtTokenFilter: 제외된 경로입니다. 필터 체인 계속 진행.");
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (accessToken == null) {
            log.error("❌ JwtTokenFilter: accessToken 쿠키가 존재하지 않습니다. 로그인 필요.");
            throw new DoNotLoginException();
        }

        String userId = JwtUtil.getUserId(accessToken, SECRET_KEY);
        Users loginUser = authService.getLoginUser(userId);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getUniqueId(), null, null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isExcludedPath(String requestURI) {
        return EXCLUDED_PATHS.stream().anyMatch(requestURI::matches);
    }
}