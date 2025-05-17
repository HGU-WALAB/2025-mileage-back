package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.profile.domain.Info;
import com.csee.swplus.mileage.profile.dto.InfoRequestDto;
import com.csee.swplus.mileage.profile.dto.InfoResponseDto;
import com.csee.swplus.mileage.profile.mapper.InfoMapper;
import com.csee.swplus.mileage.profile.repository.ProfileInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileInfoService {
    private final InfoMapper infoMapper;
    private final ProfileInfoRepository profileInfoRepository;

    public InfoResponseDto getInfo(String studentId) {
        infoMapper.insertIfNotExists(studentId);
        return infoMapper.findInfoByUserId(studentId);
    }

    @Transactional
    public void patchInfo(String studentId, InfoRequestDto infoRequestDto) {
        Info info = profileInfoRepository.findBySnum(studentId).orElseThrow(() -> new RuntimeException("Not found"));

        if (infoRequestDto.getProfileImageUrl() != null) info.setProfileImageUrl(infoRequestDto.getProfileImageUrl());
        if (infoRequestDto.getSelfDescription() != null) info.setSelfDescription(infoRequestDto.getSelfDescription());
        if (infoRequestDto.getJob() != null) info.setJob(infoRequestDto.getJob());
        if (infoRequestDto.getGithubLink() != null) info.setGithubLink(infoRequestDto.getGithubLink());
        if (infoRequestDto.getInstagramLink() != null) info.setInstagramLink(infoRequestDto.getInstagramLink());
        if (infoRequestDto.getBlogLink() != null) info.setBlogLink(infoRequestDto.getBlogLink());
        if (infoRequestDto.getLinkedinLink() != null) info.setLinkedinLink(infoRequestDto.getLinkedinLink());
    }
}
