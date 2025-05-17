package com.csee.swplus.mileage.profile.not_shared.cotroller;

import com.csee.swplus.mileage.award.dto.AwardResponseDto;
import com.csee.swplus.mileage.profile.not_shared.dto.InfoResponseDto;
import com.csee.swplus.mileage.profile.not_shared.service.ProfileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mileage")
@RequiredArgsConstructor
@Slf4j
public class NotSharedProfileController {
    private final ProfileInfoService profileInfoService;

    @GetMapping("/profile/myinfo")
    public ResponseEntity<InfoResponseDto> getInfo() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileInfoService.getInfo(currentUserId) // ← 단일 객체 반환하도록 서비스도 수정
        );
    }
}
