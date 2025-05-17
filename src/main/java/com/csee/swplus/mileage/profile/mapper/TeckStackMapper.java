package com.csee.swplus.mileage.profile.mapper;

import com.csee.swplus.mileage.profile.dto.TeckStackResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeckStackMapper {
    TeckStackResponseDto findTeckStackByUserId(@Param("studentId") String studentId);
    int insertIfNotExists(@Param("studentId") String studentId);
}
