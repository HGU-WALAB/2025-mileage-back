package com.csee.swplus.mileage.archive.project.service;

import com.csee.swplus.mileage.archive.award.domain.Award;
import com.csee.swplus.mileage.archive.award.dto.AwardRequestDto;
import com.csee.swplus.mileage.archive.project.domain.Project;
import com.csee.swplus.mileage.archive.project.dto.*;
import com.csee.swplus.mileage.archive.project.mapper.ProjectMapper;
import com.csee.swplus.mileage.archive.project.repository.ProjectRepository;
import com.csee.swplus.mileage.etcSubitem.domain.EtcSubitemFile;
import com.csee.swplus.mileage.file.FileService;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    private String baseUrl = "/api/mileage/project/image/";

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
                        baseUrl + entity.getThumbnail(),
                        Arrays.stream(entity.getTechStack().split(","))
                                .map(String::trim)
                                .collect(Collectors.toList())
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
                    baseUrl + res.getThumbnail(),
                    Arrays.stream(res.getTechStack().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList())
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
                                          LocalDate startDate,
                                          LocalDate endDate,
                                          MultipartFile file,
                                          List<String> techStack) {
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
            project.setStartDate(startDate);
            project.setEndDate(endDate);

            // techStack
            project.setTechStack(String.join(",", techStack));

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

//    public MessageResponseDto patchProject(String studentId, int awardId, AwardRequestDto awardRequestDto) {
//        try {
//            Award award = awardRepository.findById(awardId)
//                    .orElseThrow(() -> new RuntimeException("해당 수상 내역을 찾을 수 없습니다."));
//
//            award.setAwardDate(awardRequestDto.getAwardDate());
//            award.setAwardYear(awardRequestDto.getAwardYear());
//            award.setContestName(awardRequestDto.getContestName());
//            award.setAwardName(awardRequestDto.getAwardName());
//            award.setAwardType(awardRequestDto.getAwardType());
//            award.setOrganization(awardRequestDto.getOrganization());
//
//            awardRepository.save(award);
//            return new MessageResponseDto("수상 내역이 수정되었습니다.");
//        } catch (Exception e) {
//            log.error("⚠️ 수상 내역 수정 중 오류 발생: ", e);
//            throw new RuntimeException("수상 내역 수정 중 오류가 발생했습니다.");
//        }
//    }
//
//    public MessageResponseDto deleteAward(String studentId, int awardId) {
//        try {
//            awardRepository.deleteById(awardId);
//
//            return new MessageResponseDto("수상 내역이 삭제되었습니다.");
//        } catch (Exception e) {
//            log.error("⚠️ 수상 내역 삭제 중 오류 발생: ", e);
//            throw new RuntimeException("수상 내역 삭제 중 오류가 발생했습니다.");
//        }
//    }
}