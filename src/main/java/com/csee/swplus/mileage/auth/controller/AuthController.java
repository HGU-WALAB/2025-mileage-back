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
//@CrossOrigin(origins = {"http://localhost:5173", "http://walab.handong.edu"})
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final HisnetLoginService hisnetLoginService;

    @PostMapping("/login")
    public ResponseEntity<PublicLoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        log.info("📩 LoginRequest received: hisnetToken = {}", request.getToken());

        // 🔥 프론트에서 받은 token 사용 ❌
        // 🔹 AuthDto 객체를 프론트에서 받은 데이터를 기반으로 만들지 않음
        AuthDto authDto = hisnetLoginService.callHisnetLoginApi(AuthDto.from(request));

        log.info("AuthDto values: {}", authDto);

        // ✅ 새롭게 로그인 처리
        LoginResponse loginResponse = LoginResponse.from(authService.login(authDto));

        log.info("Full LoginResponse: {}", loginResponse);

        // ✅ 새로운 AccessToken 및 RefreshToken 생성
        String accessToken = authService.createAccessToken(
                loginResponse.getStudentId(),
                loginResponse.getStudentName(),
                loginResponse.getStudentEmail()
        );
        String refreshToken = authService.createRefreshToken(
                loginResponse.getStudentId(),
                loginResponse.getStudentName()
        );

        // ✅ 쿠키 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "accessToken=" + accessToken + "; HttpOnly; Path=/; Max-Age=7200; SameSite=Lax;");
        headers.add(HttpHeaders.SET_COOKIE, "refreshToken=" + refreshToken + "; HttpOnly; Path=/; Max-Age=604800; SameSite=Lax;");

        // ✅ PublicLoginResponse 생성 (프론트에 전달)
        PublicLoginResponse publicResponse = PublicLoginResponse.from(loginResponse);
        log.info("PublicLoginResponse: {}", publicResponse);

        return ResponseEntity.ok()
                .headers(headers)
                .body(publicResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie accessCookie = new Cookie("accessToken", "");
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0); // 쿠키 삭제

        Cookie refreshCookie = new Cookie("refreshToken", "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0); // 쿠키 삭제

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        return ResponseEntity.ok().build();
    }
}