package com.csee.swplus.mileage.award.mapper;

import com.csee.swplus.mileage.award.dto.AwardResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AwardMapper {
    List<AwardResponseDto> findAllAwards(@Param("studentId") String studentId);
}
