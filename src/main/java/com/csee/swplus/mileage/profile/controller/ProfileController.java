package com.csee.swplus.mileage.profile.controller;

import com.csee.swplus.mileage.profile.dto.*;
import com.csee.swplus.mileage.profile.service.*;
import com.csee.swplus.mileage.profile.dto.MessageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/mileage")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileInfoService profileInfoService;
    private final ProfileTechStackService profileTechStackService;
    private final ProfileProjectService profileProjectService;
    private final ProfileAwardService profileAwardService;
    private final ProfileMileageService profileMileageService;

    @Value("${file.profile-dir}")
    private String FILE_DIRECTORY;

    private String getContentType(String filename) {
        String lowerName = filename.toLowerCase();
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (lowerName.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // fallback
        }
    }

    @GetMapping("/profile/image/{filename}")
    public ResponseEntity<Resource> getProfileThumbnail (@PathVariable String filename) {
        try {
            Path filePath = Paths.get(FILE_DIRECTORY).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = getContentType(filename);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            log.error("❗️ profile-dir 파일 경로 오류", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<InfoResponseDto> getInfo() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileInfoService.getInfo(currentUserId)
        );
    }

    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> patchInfo(
            @RequestParam("self_description") String self_description,
            @RequestParam("job") String job,
            @RequestParam("github_link") String github_link,
            @RequestParam("instagram_link") String instagram_link,
            @RequestParam("blog_link") String blog_link,
            @RequestParam("linkedin_link") String linkedin_link,
            @RequestPart(value = "profile_image_url", required = false) MultipartFile profile_image_url
    ) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            if (profile_image_url != null && !profile_image_url.isEmpty()) {
                String originalFilename = profile_image_url.getOriginalFilename();

                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
                }

                List<String> allowedExtenstions = Arrays.asList("png", "jpg", "jpeg");

                if (!allowedExtenstions.contains(extension)) {
                    return ResponseEntity.badRequest().body(new MessageResponse("지원하지 않는 파일 형식입니다."));
                }
            }

            return ResponseEntity.ok(
                    profileInfoService.patchInfo(
                            currentUserId,
                            self_description,
                            job,
                            github_link,
                            instagram_link,
                            blog_link,
                            linkedin_link,
                            profile_image_url
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/share/{studentId}")
    public ResponseEntity<InfoResponseDto> getInfoById(@PathVariable String studentId) {
        return ResponseEntity.ok(profileInfoService.getInfo(studentId));
    }

    @GetMapping("/profile/techStack")
    public ResponseEntity<TechStackResponseDto> getTeckStack() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                profileTechStackService.getTechStack(currentUserId)
        );
    }

    @PatchMapping("/profile/techStack")
    public ResponseEntity<MessageResponse> patchTeckStack(@RequestBody TechStackRequestDto requestdto) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        profileTechStackService.patchTechStack(currentUserId, requestdto);
        return ResponseEntity.ok(new MessageResponse("프로필 내용이 수정되었습니다."));
    }

    @GetMapping("/share/{studentId}/techStack")
    public ResponseEntity<TechStackResponseDto> getTeckStack(@PathVariable String studentId) {
        return ResponseEntity.ok(profileTechStackService.getTechStack(studentId));
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