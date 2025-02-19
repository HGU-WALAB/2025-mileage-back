package com.csee.swplus.mileage.milestone.mapper;

import com.csee.swplus.mileage.milestone.dto.response.MilestoneResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MilestoneMapper {
    List<MilestoneResponseDto> findAllMilestoneCapability();
}
