package com.csee.swplus.mileage.user.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.csee.swplus.mileage.user.controller.request.UserRequest;
import com.csee.swplus.mileage.user.controller.response.UserResponse;
import com.csee.swplus.mileage.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mileage/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/{studentId}")
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable String studentId) {
        UserResponse userResponse = userService.getUserInfo(studentId);
        return ResponseEntity.ok(userResponse);
    }

//    @PatchMapping("/{studentId}")
//    public ResponseEntity<UserResponse> updateUserInfo(@PathVariable String uniqueId, @RequestBody UserRequest updateRequest) {
//        UserResponse userResponse = userService.updateUserInfo(uniqueId, updateRequest);
//        return ResponseEntity.ok(userResponse);
//    }
}