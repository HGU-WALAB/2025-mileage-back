package com.csee.swplus.mileage.scholarship.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScholarshipResponseDto {
    private Long success; // 성공 시: 장학금 신청한 학생 ID (PK 를 말하는 것이며 학번 sNum 과 다름) 반환
}