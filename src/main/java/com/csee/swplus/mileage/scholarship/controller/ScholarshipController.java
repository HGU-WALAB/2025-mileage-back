package com.csee.swplus.mileage.scholarship.controller;

import com.csee.swplus.mileage.scholarship.dto.ScholarshipRequestDto;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
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
    public ResponseEntity<MessageResponseDto> applyScholarship (
            @PathVariable String studentId,
            @RequestBody ScholarshipRequestDto requestDto) {
        // 성공 시: 장학금 신청한 학생 ID (PK 를 말하는 것이며 학번 sNum 과 다름) 반환
        // 실패 시: 에러 메세지 반환
        try {
            scholarshipService.applyScholarship(studentId, requestDto.getIsAgree());
            return ResponseEntity.ok(new MessageResponseDto(studentId));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDto(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponseDto("서버 오류 발생"));
        }
    }
}
