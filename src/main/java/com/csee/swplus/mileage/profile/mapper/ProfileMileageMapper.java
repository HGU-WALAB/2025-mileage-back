package com.csee.swplus.mileage.profile.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMileageMapper {
    int countMileagesByStudentId(@Param("studentId") String studentId);
}
