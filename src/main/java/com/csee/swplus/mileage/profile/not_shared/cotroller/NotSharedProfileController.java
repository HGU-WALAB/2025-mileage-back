package com.csee.swplus.mileage.profile.not_shared.cotroller;

import com.csee.swplus.mileage.award.dto.AwardResponseDto;
import com.csee.swplus.mileage.profile.not_shared.dto.InfoRequestDto;
import com.csee.swplus.mileage.profile.not_shared.dto.InfoResponseDto;
import com.csee.swplus.mileage.profile.not_shared.dto.MessageResponse;
import com.csee.swplus.mileage.profile.not_shared.dto.TeckStackResponseDto;
import com.csee.swplus.mileage.profile.not_shared.service.ProfileInfoService;
import com.csee.swplus.mileage.profile.not_shared.service.ProfileTeckStackService;
import com.csee.swplus.mileage.profile.not_shared.dto.MessageResponse;

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
    private final ProfileTeckStackService profileTeckStackService;

    @GetMapping("/profile/myinfo")
    public ResponseEntity<InfoResponseDto> getInfo() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileInfoService.getInfo(currentUserId) // ← 단일 객체 반환하도록 서비스도 수정
        );
    }

    @PatchMapping("/profile/myinfo")
    public ResponseEntity<MessageResponse> patchInfo(@RequestBody InfoRequestDto requestdto) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        profileInfoService.patchInfo(currentUserId, requestdto);
        return ResponseEntity.ok(new MessageResponse("프로필 내용이 수정되었습니다."));
    }

    @GetMapping("/profile/teckStack")
    public ResponseEntity<TeckStackResponseDto> getTeckStack() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileTeckStackService.getTeckStack(currentUserId) // ← 단일 객체 반환하도록 서비스도 수정
        );
    }
}
