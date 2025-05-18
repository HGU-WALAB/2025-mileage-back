package com.csee.swplus.mileage.profile.mapper;

import com.csee.swplus.mileage.profile.dto.ProfileProjectResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileProjectMapper {
    ProfileProjectResponseDto findProjectByUserId(@Param("studentId") String studentId);
    int insertIfNotExists(@Param("studentId") String studentId, @Param("projectId") Integer projectId);
}

