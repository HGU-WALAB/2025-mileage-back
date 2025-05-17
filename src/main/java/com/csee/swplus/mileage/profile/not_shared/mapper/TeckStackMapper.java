package com.csee.swplus.mileage.profile.not_shared.mapper;

import com.csee.swplus.mileage.profile.not_shared.dto.TeckStackResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeckStackMapper {
    TeckStackResponseDto findTeckStackByUserId(@Param("studentId") String studentId);
}
