package com.csee.swplus.mileage.archive.project.controller;

import com.csee.swplus.mileage.archive.project.dto.AllProjectsResponseDto;
import com.csee.swplus.mileage.archive.project.dto.ProjectResponseDto;
import com.csee.swplus.mileage.archive.project.service.ProjectService;
import com.csee.swplus.mileage.profile.dto.TechStackRequestDto;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<List<AllProjectsResponseDto>> getAllProjects() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(
                projectService.getAllProjects(currentUserId)
        );
    }

    @GetMapping("/{projectId}")
    private ResponseEntity<ProjectResponseDto> getProjectDetail(@PathVariable int projectId) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(
                projectService.getProjectDetail(currentUserId, projectId)
        );
    }

    @Value("${file.project-dir}")
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

    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getProjectThumbnail (@PathVariable String filename) {
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
            log.error("❗️ project-dir 파일 경로 오류", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> postProject (
            @RequestParam("name") String name,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam("description") String description,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "achievement", required = false) String achievement,
            @RequestParam(value = "github_link", required = false) String github_link,
            @RequestParam(value = "blog_link", required = false) String blog_link,
            @RequestParam(value = "deployed_link", required = false) String deployed_link,
            @RequestParam(value = "start_date", required = false) String start_date,
            @RequestParam(value = "end_date", required = false) String end_date,
            @RequestParam(value = "techStack", required = false) String techStackJson,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(thumbnail != null && !thumbnail.isEmpty()){
            String originalFilename = thumbnail.getOriginalFilename();

            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            }

            List<String> allowedExtenstions = Arrays.asList("png", "jpg", "jpeg");

            if (!allowedExtenstions.contains(extension)) {
                return ResponseEntity.badRequest().body(new MessageResponseDto("지원하지 않는 파일 형식입니다."));
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TechStackRequestDto techStack;
        try {
            techStack = objectMapper.readValue(techStackJson, TechStackRequestDto.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDto("techStack 파싱 오류: " + e.getMessage()));
        }

        return ResponseEntity.ok(
                projectService.postProject(currentUserId,
                        name,
                        role,
                        description,
                        content,
                        achievement,
                        github_link,
                        blog_link,
                        deployed_link,
                        start_date,
                        end_date,
                        thumbnail,
                        techStack
                )
        );
    }

    @PutMapping(value = "/{projectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> patchProject (
            @PathVariable int projectId,
            @RequestParam("name") String name,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam("description") String description,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "achievement", required = false) String achievement,
            @RequestParam(value = "github_link", required = false) String github_link,
            @RequestParam(value = "blog_link", required = false) String blog_link,
            @RequestParam(value = "deployed_link", required = false) String deployed_link,
            @RequestParam(value = "start_date", required = false) String start_date,
            @RequestParam(value = "end_date", required = false) String end_date,
            @RequestParam(value = "techStack", required = false) String techStackJson,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            if (thumbnail != null && !thumbnail.isEmpty()) {
                String originalFilename = thumbnail.getOriginalFilename();

                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
                }

                List<String> allowedExtenstions = Arrays.asList("png", "jpg", "jpeg");

                if (!allowedExtenstions.contains(extension)) {
                    return ResponseEntity.badRequest().body(new MessageResponseDto("지원하지 않는 파일 형식입니다."));
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            TechStackRequestDto techStack;

            try {
                techStack = objectMapper.readValue(techStackJson, TechStackRequestDto.class);
            } catch (IOException e) {
                return ResponseEntity.badRequest().body(new MessageResponseDto("techStack 파싱 오류: " + e.getMessage()));
            }

            return ResponseEntity.ok(
                    projectService.patchProject(currentUserId,
                            projectId,
                            name,
                            role,
                            description,
                            content,
                            achievement,
                            github_link,
                            blog_link,
                            deployed_link,
                            start_date,
                            end_date,
                            thumbnail,
                            techStack
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDto(e.getMessage()));
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponseDto> deleteProject(
            @PathVariable int projectId
    ) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                projectService.deleteProject(currentUserId, projectId)
        );
    }
}
