package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.profile.dto.AwardCountResponseDto;
import com.csee.swplus.mileage.profile.dto.MileageCountResponseDto;
import com.csee.swplus.mileage.profile.mapper.ProfileMileageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileMileageService {
    private final ProfileMileageMapper profileMileageMapper;

    public MileageCountResponseDto getMileageCount(String studentId) {
        int count = profileMileageMapper.countMileagesByStudentId(studentId);
        return new MileageCountResponseDto(count);
    }
}
