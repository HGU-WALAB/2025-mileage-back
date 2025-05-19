package com.csee.swplus.mileage.subitem.mapper;

import com.csee.swplus.mileage.subitem.dto.SubitemResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DetailMapper {
    List<SubitemResponseDto> findAllDetailSubitems(@Param("studentId") String studentId);
}