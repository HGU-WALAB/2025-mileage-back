package com.csee.swplus.mileage.profile.controller;

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
    //private final FileStorageService fileStorageService;

    @GetMapping("/profile")
    public ResponseEntity<InfoResponseDto> getInfo() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileInfoService.getInfo(currentUserId)
        );
    }

    @PatchMapping("/profile")
    public ResponseEntity<MessageResponse> patchInfo(@RequestBody InfoRequestDto requestdto) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        profileInfoService.patchInfo(currentUserId, requestdto);
        return ResponseEntity.ok(new MessageResponse("프로필 내용이 수정되었습니다."));
    }

//    @PostMapping("/profile/image")
//    public ResponseEntity<ImageUploadResponseDto> uploadProfileImage(@RequestParam("file") MultipartFile file) {
//        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // Save the file in the "profiles" subdirectory
//        String fileName = fileStorageService.storeFile(file, "profiles");
//
//        // Create the download URL
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/files/")
//                .path(fileName)
//                .toUriString();
//
//        // Update the user's profile image URL
//        profileInfoService.updateProfileImage(currentUserId, fileName);
//
//        return ResponseEntity.ok(new ImageUploadResponseDto(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize()));
//    }

    @GetMapping("/share/{studentId}")
    public ResponseEntity<InfoResponseDto> getInfoById(@PathVariable String studentId) {
        return ResponseEntity.ok(profileInfoService.getInfo(studentId));
    }

    @GetMapping("/profile/techStack")
    public ResponseEntity<TechStackResponseDto> getTeckStack() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileTeckStackService.getTechStack(currentUserId)
        );
    }

    @PatchMapping("/profile/techStack")
    public ResponseEntity<MessageResponse> patchTeckStack(@RequestBody TechStackRequestDto requestdto) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        profileTeckStackService.patchTeckStack(currentUserId, requestdto);
        return ResponseEntity.ok(new MessageResponse("프로필 내용이 수정되었습니다."));
    }

    @GetMapping("/share/{studentId}/techStack")
    public ResponseEntity<TechStackResponseDto> getTeckStack(@PathVariable String studentId) {
        return ResponseEntity.ok(profileTeckStackService.getTechStack(studentId));
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