package com.csee.swplus.mileage.milestone.mapper;

import com.csee.swplus.mileage.milestone.dto.response.*;
import com.csee.swplus.mileage.profile.dto.InfoResponseDto;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MilestoneMapper {
    List<MilestoneResponseDto> findAllMilestoneCapability();

    List<MilestonePointResponseDto> findAllMilestonePoint(@Param("studentId") String studentId, @Param("currentSemester") String current);

    List<MPResponseDto> findFilteredAverageMilestonePoint(
            @Param("term") String term,
            @Param("entryYear") String entryYear,
            @Param("major") String major,
            @Param("currentSemester") String currentSemester
    );

    List<MilestoneSemesterTotalPointResponseDto> findAllMilestoneBySemester(@Param("studentId") String studentId);

    @MapKey("subitemName")
    List<Map<String, Object>> findSuggestItemByUserId(@Param("studentId") String studentId);
}
