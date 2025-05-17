package com.csee.swplus.mileage.user.service;

import com.csee.swplus.mileage.user.entity.StudentSchool;
import com.csee.swplus.mileage.user.mapper.StudentSchoolMapper;
import com.csee.swplus.mileage.user.repository.StudentSchoolRepository;
import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.user.controller.request.UserRequest;
import com.csee.swplus.mileage.user.controller.response.UserResponse;
import com.csee.swplus.mileage.user.entity.Users;
import com.csee.swplus.mileage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final StudentSchoolRepository studentSchoolRepository;
    private final StudentSchoolMapper studentSchoolMapper;

    // 유저 정보 조회
    public UserResponse getUserInfo(String studentId) {
        Users user = userRepository.findByUniqueId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // school, major1, major2 값을 사용하여 stype을 바로 조회
        String stype = studentSchoolMapper.findStype(
                user.getDepartment(), // school
                user.getMajor1(),     // major1
                user.getMajor2()      // major2
        );

        return UserResponse.from(user, stype);
    }
}
