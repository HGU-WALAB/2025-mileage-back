package com.csee.swplus.mileage.user.service;

import com.csee.swplus.mileage.user.entity.StudentSchool;
import com.csee.swplus.mileage.user.repository.StudentSchoolRepository;
import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.user.controller.request.UserRequest;
import com.csee.swplus.mileage.user.controller.response.UserResponse;
import com.csee.swplus.mileage.user.entity.Users;
import com.csee.swplus.mileage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final StudentSchoolRepository studentSchoolRepository;


    // 유저 정보 조회
    public UserResponse getUserInfo(String studentId) {
        Users user = userRepository.findByUniqueId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 먼저 school 정보 저장 (없다면 새로 추가됨)
        saveUserSchoolInfo(studentId);

        // 저장된 StudentSchool에서 stype 가져오기
        StudentSchool studentSchool = studentSchoolRepository
                .findBySchoolAndMajor1AndMajor2(user.getDepartment(), user.getMajor1(), user.getMajor2())
                .orElseThrow(() -> new RuntimeException("StudentSchool not found after saving"));

        return UserResponse.from(user, studentSchool.getStype());
    }

    @Transactional
    public void saveUserSchoolInfo(String studentId) {
        Users user = userRepository.findByUniqueId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<StudentSchool> existingSchool = studentSchoolRepository
                .findBySchoolAndMajor1AndMajor2(user.getDepartment(), user.getMajor1(), user.getMajor2());

        if (existingSchool.isPresent()) {
            log.info("StudentSchool already exists: {}", existingSchool.get());
            return;
        }

        // 새로 저장
        StudentSchool schoolInfo = StudentSchool.from(user);
        String stype = determineStype(schoolInfo);
        schoolInfo.setStype(stype);

        log.info("Saving new StudentSchool: school={}, major1={}, major2={}, stype={}",
                schoolInfo.getSchool(), schoolInfo.getMajor1(), schoolInfo.getMajor2(), schoolInfo.getStype());

        studentSchoolRepository.save(schoolInfo);
    }

    private String determineStype(StudentSchool studentSchool) {
        String school = studentSchool.getSchool();
        String major2 = studentSchool.getMajor2();

        // 조건 1: 전산전자공학부
        if ("전산전자공학부".equals(school)) {
            return "전공";
        }

        // 조건 2: 글로벌리더십학부
        if ("글로벌리더십학부".equals(school)) {
            return "1학년";
        }

        // 조건 3: major_2가 특정 전공일 경우 "융합"
        if ("컴퓨터공학".equals(major2) || "전자공학".equals(major2) || "IT".equals(major2)) {
            return "융합";
        }

        // 조건에 맞지 않으면 기본값
        return "기타";
    }
}
