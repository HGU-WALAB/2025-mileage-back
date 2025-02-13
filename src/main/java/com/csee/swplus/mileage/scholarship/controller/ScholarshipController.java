package com.csee.swplus.mileage.scholarship.controller;

import com.csee.swplus.mileage.scholarship.dto.ScholarshipRequestDto;
import com.csee.swplus.mileage.scholarship.dto.ScholarshipResponseDto;
import com.csee.swplus.mileage.scholarship.service.ScholarshipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/apply")
@RequiredArgsConstructor
@Slf4j
public class ScholarshipController {
    private final ScholarshipService scholarshipService;

    @PostMapping("/{studentId}")
    public ResponseEntity<?> applyScholarship (
            @PathVariable int studentId,
            @RequestBody ScholarshipRequestDto requestDto) {
        try {
            scholarshipService.applyScholarship(studentId, requestDto.isAgree());
            return ResponseEntity.ok(new ScholarshipResponseDto(Integer.toString(studentId)));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ScholarshipResponseDto(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ScholarshipResponseDto("서버 오류 발생"));
        }
    }
}
