package com.csee.swplus.mileage.award.controller;

import com.csee.swplus.mileage.award.dto.AwardRequestDto;
import com.csee.swplus.mileage.award.dto.AwardResponseDto;
import com.csee.swplus.mileage.award.service.AwardService;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/award")
@RequiredArgsConstructor
@Slf4j
public class AwardController {
    private final AwardService awardService;

    @GetMapping("")
    public ResponseEntity<List<AwardResponseDto>> getAllAwards() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                awardService.getAwards(currentUserId)
        );
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<MessageResponseDto> postAward (
            @PathVariable String studentId,
            @RequestBody AwardRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                awardService.postAward(studentId, requestDto)
        );
    }
}
