package com.csee.swplus.mileage.archive.project.controller;

import com.csee.swplus.mileage.archive.project.dto.AllProjectsResponseDto;
import com.csee.swplus.mileage.archive.project.dto.ProjectEntityDto;
import com.csee.swplus.mileage.archive.project.dto.ProjectResponseDto;
import com.csee.swplus.mileage.archive.project.service.ProjectService;
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

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private ResponseEntity<List<ProjectResponseDto>> getProjectDetail(@PathVariable int projectId) {
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

    @GetMapping("/{filename}")
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
}
