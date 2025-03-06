package com.csee.swplus.mileage.auth.controller;

import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.auth.controller.request.LoginRequest;
import com.csee.swplus.mileage.auth.controller.response.LoginResponse;
import com.csee.swplus.mileage.auth.controller.response.PublicLoginResponse;
import com.csee.swplus.mileage.auth.dto.AuthDto;
import com.csee.swplus.mileage.auth.service.AuthService;
import com.csee.swplus.mileage.auth.service.HisnetLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/api/mileage/auth")
//@CrossOrigin(origins = {"http://localhost:5173"})
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final HisnetLoginService hisnetLoginService;

    @PostMapping("/login")
    public ResponseEntity<PublicLoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        log.info("📩 LoginRequest received: hisnetToken = {}", request.getToken());

        // 1. LoginRequest를 기반으로 AuthDto 생성 후 히즈넷 API 호출
        AuthDto authDto = hisnetLoginService.callHisnetLoginApi(AuthDto.from(request));
        log.info("AuthDto values: {}", authDto);

        // 2. AuthService를 통해 로그인 처리 및 LoginResponse 생성 (여기에는 토큰이 포함됨)
        LoginResponse loginResponse = LoginResponse.from(authService.login(authDto));
        log.info("Full LoginResponse: {}", loginResponse);
        log.info("CurrentSemester value in LoginResponse: {}", loginResponse.getCurrentSemester());

        // 3. 쿠키에 JWT 토큰 저장 (프론트엔드로는 토큰 없이 전달)
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "authToken=" + loginResponse.getToken() + "; HttpOnly; Path=/; Max-Age=7200");

        // 4. PublicLoginResponse 생성: 토큰을 제외한 사용자 정보만 포함
        PublicLoginResponse publicResponse = PublicLoginResponse.from(loginResponse);
        log.info("PublicLoginResponse: {}", publicResponse);

        return ResponseEntity.ok()
                .headers(headers)
                .body(publicResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("authToken", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // 쿠키 삭제

        response.addCookie(jwtCookie);
        return ResponseEntity.ok().build();
    }
}