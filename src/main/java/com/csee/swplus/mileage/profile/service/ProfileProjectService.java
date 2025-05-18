package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.archive.project.repository.ProjectRepository;
import com.csee.swplus.mileage.profile.domain.ProfileProject;
import com.csee.swplus.mileage.profile.domain.TechStack;
import com.csee.swplus.mileage.profile.dto.ProfileProjectRequestDto;
import com.csee.swplus.mileage.profile.dto.ProfileProjectResponseDto;
import com.csee.swplus.mileage.profile.dto.TeckStackRequestDto;
import com.csee.swplus.mileage.profile.mapper.ProfileProjectMapper;
import com.csee.swplus.mileage.profile.repository.ProfileProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileProjectService {
    private final ProfileProjectMapper projectMapper;
    private final ProfileProjectRepository profileProjectRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ProfileProjectResponseDto getProfileProject(String studentId) {
        ProfileProjectResponseDto dto = projectMapper.findProjectByUserId(studentId);

        if (dto == null) {
            projectMapper.insertIfNotExists(studentId, null);
            return new ProfileProjectResponseDto(null, null);
        }
        return dto;
    }

    @Transactional
    public void patchProfileProject(String studentId, ProfileProjectRequestDto profileProjectRequestDto) {
        ProfileProject profileProject = profileProjectRepository.findBySnum(studentId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (profileProjectRequestDto.getProjectId() != null) {
            profileProject.setProjectId(profileProjectRequestDto.getProjectId());
            profileProjectRepository.save(profileProject);
        }
    }
}