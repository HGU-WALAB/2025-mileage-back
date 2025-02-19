package com.csee.swplus.mileage.milestone.service;

import com.csee.swplus.mileage.milestone.dto.response.MilestonePointResponseDto;
import com.csee.swplus.mileage.milestone.dto.response.MilestoneResponseDto;
import com.csee.swplus.mileage.milestone.dto.response.MilestoneSemesterResponseDto;
import com.csee.swplus.mileage.milestone.mapper.MilestoneMapper;
import com.csee.swplus.mileage.util.DataWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MilestoneService {
    private final MilestoneMapper milestoneMapper;

    public DataWrapper getMilestoneCapabilities() {
        List<MilestoneResponseDto> res = milestoneMapper.findAllMilestoneCapability();
        log.info("📝 findAllMilestoneCapability 결과 - res: {}", res);
        return new DataWrapper(res);
    }

    public DataWrapper getMilestonePoint(String studentId) {
        List<MilestonePointResponseDto> res = milestoneMapper.findAllMilestonePoint();
        log.info("📝 findAllMilestonePoint 결과 - res: {}", res);
        return new DataWrapper(res);
    }

    public DataWrapper getMilestoneSemester(String studentId) {
        List<MilestoneSemesterResponseDto> res = milestoneMapper.findAllMilestoneBySemester();
        log.info("📝 findAllMilestoneBySemester 결과 - res: {}", res);
        return new DataWrapper(res);
    }
}
