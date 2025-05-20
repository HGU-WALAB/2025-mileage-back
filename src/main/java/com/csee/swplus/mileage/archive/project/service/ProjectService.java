package com.csee.swplus.mileage.archive.project.service;

import com.csee.swplus.mileage.archive.project.domain.Project;
import com.csee.swplus.mileage.archive.project.dto.*;
import com.csee.swplus.mileage.archive.project.mapper.ProjectMapper;
import com.csee.swplus.mileage.archive.project.repository.ProjectRepository;
import com.csee.swplus.mileage.file.FileService;
import com.csee.swplus.mileage.profile.domain.TechStack;
import com.csee.swplus.mileage.profile.dto.TechStackRequestDto;
import com.csee.swplus.mileage.profile.dto.TechStackResponseDto;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // not null 또는 final 인 필드를 받는 생성자
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final FileService fileService;

    @Value("${file.project-upload-dir}")
    @Getter
    private String uploadDir;

    public List<AllProjectsResponseDto> getAllProjects(String studentId) {
        List<AllProjectsEntityDto> res = projectMapper.findAllProjects(studentId);
        log.info("📝 AllProjectsEntityDto 결과 - res: {}", res);

        return res.stream()
                .map(entity -> new AllProjectsResponseDto(
                        entity.getProjectId(),
                        entity.getName(),
                        entity.getRole(),
                        entity.getStart_date(),
                        entity.getEnd_date(),
                        entity.getRegDate(),
                        entity.getModDate(),
                        entity.getThumbnail(),
                        new TechStackResponseDto(
                                (entity.getTechStack() == null || entity.getTechStack().isEmpty())
                                        ? Collections.emptyList() :
                                        Arrays.stream(entity.getTechStack().split(","))
                                .map(String::trim)
                                .collect(Collectors.toList()))
                ))
                .collect(Collectors.toList());
    }

    public ProjectResponseDto getProjectDetail(String studentId, int projectId) {
        ProjectEntityDto res = projectMapper.findProjectDetail(studentId, projectId);
        log.info("📝 ProjectEntityDto 결과 - res: {}", res);

        return new ProjectResponseDto(
                    res.getProjectId(),
                    res.getName(),
                    res.getRole(),
                    res.getDescription(),
                    res.getContent(),
                    res.getAchievement(),
                    res.getGithub_link(),
                    res.getBlog_link(),
                    res.getDeployed_link(),
                    res.getStart_date(),
                    res.getEnd_date(),
                    res.getRegDate(),
                    res.getModDate(),
                    res.getThumbnail(),
                    new TechStackResponseDto(
                            (res.getTechStack() == null || res.getTechStack().isEmpty())
                                    ? Collections.emptyList() :
                                    Arrays.stream(res.getTechStack().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList()))
                );
    }

    public MessageResponseDto postProject(String studentId,
                                          String name,
                                          String role,
                                          String description,
                                          String content,
                                          String achievement,
                                          String githubLink,
                                          String blogLink,
                                          String deployedLink,
                                          String startDate,
                                          String endDate,
                                          MultipartFile file,
                                          TechStackRequestDto techStack) {
        try {
            Project project = new Project();

            // fixed values
            project.setSnum(studentId);
            project.setName(name);
            project.setRole(role);
            project.setDescription(description);
            project.setContent(content);
            project.setAchievement(achievement);
            project.setGithubLink(githubLink);
            project.setBlogLink(blogLink);
            project.setDeployedLink(deployedLink);

            if (startDate != null && !startDate.isEmpty()) {
                try {
                    LocalDate parsedStartDate = LocalDate.parse(startDate);
                    project.setStartDate(parsedStartDate);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. (예: YYYY-MM-DD)");
                }
            } else {
                project.setStartDate(null);
            }

            if (endDate != null && !endDate.isEmpty()) {
                try {
                    LocalDate parsedEndDate = LocalDate.parse(endDate);
                    project.setEndDate(parsedEndDate);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. (예: YYYY-MM-DD)");
                }
            } else {
                project.setEndDate(null);
            }

            // techStack
            List<String> ts = techStack.getTechStack();
            String joined = "";
            if (ts != null) {
                joined = String.join(",", ts);
            }
            project.setTechStack(joined);

            // file
            if (file != null && !file.isEmpty()) {
//                실제 파일 저장
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 파일 확장자 추출
                String uniqueFilename = UUID.randomUUID().toString() + extension; // 고유한 파일명 생성 후 원본 확장자를 붙여 새로운 파일명 생성

                String savedFilename = fileService.saveFile(file, uploadDir, uniqueFilename);
                log.info("savedFilename: {}", savedFilename);

//                파일 관련 정보 DB에 저장
                project.setThumbnail(savedFilename);
            }

            projectRepository.save(project);
            return new MessageResponseDto("프로젝트가 등록되었습니다.");
        } catch (IOException e) {
            log.error("⚠️ 프로젝트 파일 저장 중 오류 발생: ", e);
            throw new RuntimeException("프로젝트 등록 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("⚠️ 프로젝트 등록 중 오류 발생: ", e);
            throw new RuntimeException("프로젝트 등록 중 오류가 발생했습니다.");
        }
    }

    public MessageResponseDto patchProject(String studentId,
                                          int projectId,
                                          String name,
                                          String role,
                                          String description,
                                          String content,
                                          String achievement,
                                          String githubLink,
                                          String blogLink,
                                          String deployedLink,
                                          String startDate,
                                          String endDate,
                                          MultipartFile file,
                                          TechStackRequestDto techStack) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("해당 프로젝트를 찾을 수 없습니다."));

            if (!project.getSnum().equals(studentId)) {
                throw new RuntimeException("해당 프로젝트에 대한 수정 권한이 없습니다.");
            }

            if (name != null && !name.isEmpty()) {
                project.setName(name);
            }

            if (role != null && !role.isEmpty()) {
                project.setRole(role);
            }

            if (description != null) {
                project.setDescription(description);
            }

            if (content != null) {
                project.setContent(content);
            }

            if (achievement != null) {
                project.setAchievement(achievement);
            }

            if (githubLink != null) {
                project.setGithubLink(githubLink);
            }

            if (blogLink != null) {
                project.setBlogLink(blogLink);
            }

            if (deployedLink != null) {
                project.setDeployedLink(deployedLink);
            }

            if (startDate != null && !startDate.isEmpty()) {
                try {
                    LocalDate parsedStartDate = LocalDate.parse(startDate);
                    project.setStartDate(parsedStartDate);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. (예: YYYY-MM-DD)");
                }
            } else {
                project.setStartDate(null);
            }

            if (endDate != null && !endDate.isEmpty()) {
                try {
                    LocalDate parsedEndDate = LocalDate.parse(endDate);
                    project.setEndDate(parsedEndDate);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. (예: YYYY-MM-DD)");
                }
            } else {
                project.setEndDate(null);
            }

            List<String> ts = techStack.getTechStack();
            String joined = "";
            if (ts != null) {
                joined = String.join(",", ts);
            }
            project.setTechStack(joined);

            if (file != null && !file.isEmpty()) {
                if (project.getThumbnail() != null && !project.getThumbnail().isEmpty()) {
                    fileService.deleteFile(project.getThumbnail(), uploadDir);
                }

                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 파일 확장자 추출
                String uniqueFilename = UUID.randomUUID().toString() + extension; // 고유한 파일명 생성

                String savedFilename = fileService.saveFile(file, uploadDir, uniqueFilename);
                log.info("savedFilename: {}", savedFilename);

                project.setThumbnail(savedFilename);
            }

            projectRepository.save(project);
            return new MessageResponseDto("프로젝트가 수정되었습니다.");
        } catch (IOException e) {
            log.error("⚠️ 프로젝트 파일 저장 중 오류 발생: ", e);
            throw new RuntimeException("프로젝트 수정 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("⚠️ 프로젝트 수정 중 오류 발생: ", e);
            throw new RuntimeException("프로젝트 수정 중 오류가 발생했습니다.");
        }
    }

    public MessageResponseDto deleteProject(String studentId, int projectId) {
        try {
            String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!currentUserId.equals(studentId)) {
                throw new Exception("검증되지 않은 사용자입니다.");
            }

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("해당 프로젝트를 찾을 수 없습니다."));

            if (project.getThumbnail() != null) {
                fileService.deleteFile(project.getThumbnail(), uploadDir);
            }

            projectRepository.deleteById(projectId);
            return new MessageResponseDto("프로젝트가 삭제되었습니다.");
        } catch (Exception e) {
            log.error("⚠️ 프로젝트 삭제 중 오류 발생: ", e);
            throw new RuntimeException("프로젝트 삭제 중 오류가 발생했습니다.");
        }
    }
}