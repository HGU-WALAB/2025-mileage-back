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

        if (infoRequestDto.getProfile_image_url() != null) info.setProfileImageUrl(infoRequestDto.getProfile_image_url());
        if (infoRequestDto.getSelf_description() != null) info.setSelfDescription(infoRequestDto.getSelf_description());
        if (infoRequestDto.getJob() != null) info.setJob(infoRequestDto.getJob());
        if (infoRequestDto.getGithub_link() != null) info.setGithubLink(infoRequestDto.getGithub_link());
        if (infoRequestDto.getInstagram_link() != null) info.setInstagramLink(infoRequestDto.getInstagram_link());
        if (infoRequestDto.getBlog_link() != null) info.setBlogLink(infoRequestDto.getBlog_link());
        if (infoRequestDto.getLinkedin_link() != null) info.setLinkedinLink(infoRequestDto.getLinkedin_link());
    }
}
