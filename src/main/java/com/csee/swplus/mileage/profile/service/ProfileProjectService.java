package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.archive.project.dto.ProjectResponseDto;
import com.csee.swplus.mileage.archive.project.service.ProjectService;
import com.csee.swplus.mileage.profile.domain.ProfileProject;
import com.csee.swplus.mileage.profile.dto.ProfileProjectRequestDto;
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
    private final ProjectService projectService;
    private final ProfileProjectMapper projectMapper;
    private final ProfileProjectRepository profileProjectRepository;

    @Transactional
    public ProjectResponseDto getProfileProject(String studentId) {
        Integer topProjectId = projectMapper.findTopProjectIdByUserId(studentId);

        if (topProjectId == null) {
            return null;
        }

        ProjectResponseDto profileProjectResponseDto = projectService.getProjectDetail(studentId, topProjectId);
        if (profileProjectResponseDto == null) {
            projectMapper.insertIfNotExists(studentId, null);
            return null;
        }
        
        return profileProjectResponseDto;
    }

    @Transactional
    public void patchProfileProject(String studentId, ProfileProjectRequestDto profileProjectRequestDto) {
        ProfileProject profileProject = profileProjectRepository.findBySnum(studentId);

        if(profileProject == null) {
            profileProject = new ProfileProject(studentId, profileProjectRequestDto.getProjectId());
        }

        if (profileProjectRequestDto.getProjectId() != null) {
            profileProject.setProjectId(profileProjectRequestDto.getProjectId());
        }

        profileProjectRepository.save(profileProject);
    }
}