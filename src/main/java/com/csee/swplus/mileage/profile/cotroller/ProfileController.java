package com.csee.swplus.mileage.profile.cotroller;

import com.csee.swplus.mileage.archive.award.dto.AwardResponseDto;
import com.csee.swplus.mileage.profile.dto.*;
import com.csee.swplus.mileage.profile.service.*;
import com.csee.swplus.mileage.profile.dto.MessageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mileage")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileInfoService profileInfoService;
    private final ProfileTeckStackService profileTeckStackService;
    private final ProfileProjectService profileProjectService;
    private final ProfileAwardService profileAwardService;
    private final ProfileMileageService profileMileageService;

    @GetMapping("/profile")
    public ResponseEntity<InfoResponseDto> getInfo() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileInfoService.getInfo(currentUserId) // ← 단일 객체 반환하도록 서비스도 수정
        );
    }

    @PatchMapping("/profile")
    public ResponseEntity<MessageResponse> patchInfo(@RequestBody InfoRequestDto requestdto) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        profileInfoService.patchInfo(currentUserId, requestdto);
        return ResponseEntity.ok(new MessageResponse("프로필 내용이 수정되었습니다."));
    }

    @GetMapping("/share/{studentId}")
    public ResponseEntity<InfoResponseDto> getInfoById(@PathVariable String studentId) {
        return ResponseEntity.ok(profileInfoService.getInfo(studentId));
    }

    @GetMapping("/profile/teckStack")
    public ResponseEntity<TeckStackResponseDto> getTeckStack() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileTeckStackService.getTeckStack(currentUserId) // ← 단일 객체 반환하도록 서비스도 수정
        );
    }

    @PatchMapping("/profile/teckStack")
    public ResponseEntity<MessageResponse> patchTeckStack(@RequestBody TeckStackRequestDto requestdto) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        profileTeckStackService.patchTeckStack(currentUserId, requestdto);
        return ResponseEntity.ok(new MessageResponse("프로필 내용이 수정되었습니다."));
    }

    @GetMapping("/share/{studentId}/teckStack")
    public ResponseEntity<TeckStackResponseDto> getTeckStack(@PathVariable String studentId) {
        return ResponseEntity.ok(profileTeckStackService.getTeckStack(studentId));
    }

    @GetMapping("/project/top")
    public ResponseEntity<ProfileProjectResponseDto> getProfileProject() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileProjectService.getProfileProject(currentUserId)
        );
    }

    @PatchMapping("/project/top")
    public ResponseEntity<MessageResponse> patchProfileProject(@RequestBody ProfileProjectRequestDto requestdto) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        profileProjectService.patchProfileProject(currentUserId, requestdto);
        return ResponseEntity.ok(new MessageResponse("프로필 내용이 수정되었습니다."));
    }

    @GetMapping("/share/{studentId}/projectTop")
    public ResponseEntity<ProfileProjectResponseDto> getProfileProject(@PathVariable String studentId) {
        return ResponseEntity.ok(profileProjectService.getProfileProject(studentId));
    }

    @GetMapping("/share/{studentId}/award")
    public ResponseEntity<AwardCountResponseDto> getAwardCount(@PathVariable String studentId) {
        return ResponseEntity.ok(profileAwardService.getAwardCount(studentId));
    }

    @GetMapping("/share/{studentId}/mileage")
    public ResponseEntity<MileageCountResponseDto> getMileageCount(@PathVariable String studentId) {
        return ResponseEntity.ok(profileMileageService.getMileageCount(studentId));
    }
}
