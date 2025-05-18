package com.csee.swplus.mileage.profile.cotroller;

import com.csee.swplus.mileage.profile.dto.*;
import com.csee.swplus.mileage.profile.service.ProfileInfoService;
import com.csee.swplus.mileage.profile.service.ProfileProjectService;
import com.csee.swplus.mileage.profile.service.ProfileTeckStackService;
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

    @PostMapping("/share/myinfo")
    public ResponseEntity<InfoResponseDto> getInfoById(@RequestBody StudentIdRequestDto request) {
        return ResponseEntity.ok(profileInfoService.getInfo(request.getStudentId()));
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

    @PostMapping("/share/teckStack")
    public ResponseEntity<TeckStackResponseDto> getTeckStack(@RequestBody StudentIdRequestDto request) {
        return ResponseEntity.ok(profileTeckStackService.getTeckStack(request.getStudentId()));
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

    @PostMapping("/share/proejctTop")
    public ResponseEntity<ProfileProjectResponseDto> getProfileProject(@RequestBody StudentIdRequestDto request) {
        return ResponseEntity.ok(profileProjectService.getProfileProject(request.getStudentId()));
    }
}
