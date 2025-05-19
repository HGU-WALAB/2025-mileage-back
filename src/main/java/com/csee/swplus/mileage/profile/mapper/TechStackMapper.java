package com.csee.swplus.mileage.profile.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TechStackMapper {
    String findTechStackByUserId(@Param("studentId") String studentId); // String 반환
    int insertIfNotExists(@Param("studentId") String studentId);
}