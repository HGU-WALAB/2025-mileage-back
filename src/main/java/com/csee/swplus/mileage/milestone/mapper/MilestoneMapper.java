package com.csee.swplus.mileage.milestone.mapper;

import com.csee.swplus.mileage.milestone.dto.response.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MilestoneMapper {
    List<MilestoneResponseDto> findAllMilestoneCapability();

    List<MilestonePointResponseDto> findAllMilestonePoint(@Param("studentId") int studentId);
    List<MPResponseDto> findFilteredAverageMilestonePoint(
            @Param("school") String school,
            @Param("semester") Integer semester,
            @Param("snum") String snum
    );

//    List<MilestoneSemesterResponseDto> findEachMilestoneBySemester(@Param("studentId") int studentId);

    List<MilestoneSemesterTotalPointResponseDto> findAllMilestoneBySemester(@Param("studentId") int studentId);

}
