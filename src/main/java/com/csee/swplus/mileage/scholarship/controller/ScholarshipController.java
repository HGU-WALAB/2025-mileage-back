package com.csee.swplus.mileage.scholarship.controller;

import com.csee.swplus.mileage.scholarship.dto.ScholarshipRequestDto;
import com.csee.swplus.mileage.scholarship.dto.ScholarshipResponseDto;
import com.csee.swplus.mileage.setting.entity.Manager;
import com.csee.swplus.mileage.setting.service.ManagerService;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import com.csee.swplus.mileage.scholarship.service.ScholarshipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/apply")
@RequiredArgsConstructor
@Slf4j
public class ScholarshipController {
    private final ScholarshipService scholarshipService;
    private final ManagerService managerService;

    @PostMapping("/{studentId}")
    public ResponseEntity<MessageResponseDto> applyScholarship (
            @PathVariable String studentId,
            @RequestBody ScholarshipRequestDto requestDto) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Manager manager = managerService.getRegisterDate();
//            log.info("manager service 불러와짐! {}", manager.getRegStart());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime regStart = LocalDateTime.parse(manager.getRegStart(), formatter);
            LocalDateTime regEnd = LocalDateTime.parse(manager.getRegEnd(), formatter);
//            log.info("reg date 변환 잘 됨 {}", regStart);

            if (now.isAfter(regEnd) || now.isBefore(regStart)) {
                log.error("장학금 신청 기간 아님");
                throw new IllegalStateException("장학금 신청 기간이 아닙니다. 신청 가능 기간: " + regStart + " ~ " + regEnd);
            }

            scholarshipService.applyScholarship(studentId, requestDto.getIsAgree());
            return ResponseEntity.ok(new MessageResponseDto(studentId));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDto(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponseDto("서버 오류 발생"));
        }
    }
    //@CrossOrigin(origins = "http://walab.handong.edu", allowCredentials = "true")  // CORS 설정
    @GetMapping("/status")
    public ResponseEntity<ScholarshipResponseDto> getScholarshipStatus() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        ScholarshipResponseDto response = scholarshipService.getIsApplyStatus(currentUserId);
        return ResponseEntity.ok(response);
    }

}
