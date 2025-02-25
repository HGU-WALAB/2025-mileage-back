package com.csee.swplus.mileage.auth.controller.response;

import com.csee.swplus.mileage.auth.dto.AuthDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private String studentId;
    private String studentName;
    private String studentEmail;
    private String department;
    private String major1;
    private String major2;
    private Integer grade;
    private Integer term;

    public static LoginResponse from(AuthDto authDto) {
        return LoginResponse.builder()
                .token(authDto.getToken())
                .studentId(authDto.getStudentId())
                .studentName(authDto.getStudentName())
                .studentEmail(authDto.getStudentEmail())
                .department(authDto.getDepartment())
                .major1(authDto.getMajor1())
                .major2(authDto.getMajor2())
                .grade(authDto.getGrade())
                .term(authDto.getTerm())
                .build();
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", department='" + department + '\'' +
                ", major1='" + major1 + '\'' +
                ", major2='" + major2 + '\'' +
                ", grade=" + grade +
                ", term=" + term +
                '}';
    }
}