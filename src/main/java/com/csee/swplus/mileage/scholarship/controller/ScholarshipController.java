package com.csee.swplus.mileage.scholarship.controller;

import com.csee.swplus.mileage.scholarship.dto.ScholarshipRequestDto;
import com.csee.swplus.mileage.scholarship.dto.ScholarshipResponseDto;
import com.csee.swplus.mileage.scholarship.service.ScholarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ScholarshipController {
    private final ScholarshipService scholarshipService;

    @PostMapping("/{studentId}")
    public ResponseEntity<?> applyScholarship (
            @PathVariable long studentId,
            @RequestBody ScholarshipRequestDto requestDto) {
        try {
            scholarshipService.applyScholarship(studentId, requestDto.isAgree());
            return ResponseEntity.ok(new ScholarshipResponseDto(studentId));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "서버 오류 발생"));
        }
    }
}
