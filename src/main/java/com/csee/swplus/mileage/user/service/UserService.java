package com.csee.swplus.mileage.user.service;

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

    // 유저 정보 조회
    public UserResponse getUserInfo(String studentId) {
        Users user = userRepository.findByUniqueId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserResponse.from(user);
    }
}
