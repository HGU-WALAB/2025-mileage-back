package com.csee.swplus.mileage.profile.mapper;

import com.csee.swplus.mileage.profile.dto.InfoResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InfoMapper {
    InfoResponseDto findInfoByUserId(@Param("studentId") String studentId);
    int insertIfNotExists(@Param("studentId") String studentId);
}
