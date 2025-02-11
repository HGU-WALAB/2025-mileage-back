package com.csee.swplus.mileage.auth.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 기본 생성자(파라미터 없는) 자동 생성
@NoArgsConstructor
//모든 필드를 파라미터로 가지는 생성자 하나 자동 생성
@AllArgsConstructor
@Getter
public class LoginRequest {
    private String hisnetToken;
}