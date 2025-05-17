package com.csee.swplus.mileage.archive.project.service;

import com.csee.swplus.mileage.archive.project.dto.AllProjectsEntityDto;
import com.csee.swplus.mileage.archive.project.dto.AllProjectsResponseDto;
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

    public List<AllProjectsResponseDto> getAllProjects(String studentId) {
        List<AllProjectsEntityDto> res = projectMapper.findAllProjects(studentId);
        log.info("📝 AllProjectsEntityDto 결과 - res: {}", res);

        String baseUrl = "/api/mileage/project/";

        return res.stream()
                .map(entity -> new AllProjectsResponseDto(
                        entity.getProjectId(),
                        entity.getName(),
                        entity.getRole(),
                        entity.getStartDate(),
                        entity.getEndDate(),
                        entity.getRegDate(),
                        entity.getModDate(),
                        baseUrl + entity.getThumbnail(),
                        Arrays.stream(entity.getTechStack().split(","))
                                .map(String::trim)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}