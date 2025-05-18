package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.profile.dto.AwardCountResponseDto;
import com.csee.swplus.mileage.profile.mapper.ProfileAwardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileAwardService {
    private final ProfileAwardMapper profileAwardMapper;

    public AwardCountResponseDto getAwardCount(String studentId) {
        int count = profileAwardMapper.countAwardsByStudentId(studentId);
        return new AwardCountResponseDto(count);
    }
}
