package com.csee.swplus.mileage.archive.project.service;

import com.csee.swplus.mileage.archive.project.dto.AllProjectsEntityDto;
import com.csee.swplus.mileage.archive.project.dto.AllProjectsResponseDto;
import com.csee.swplus.mileage.archive.project.dto.ProjectEntityDto;
import com.csee.swplus.mileage.archive.project.dto.ProjectResponseDto;
import com.csee.swplus.mileage.archive.project.mapper.ProjectMapper;
import com.csee.swplus.mileage.archive.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // not null 또는 final 인 필드를 받는 생성자
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    private String baseUrl = "/api/mileage/project/image/";

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
}