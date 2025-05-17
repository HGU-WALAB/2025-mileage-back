package com.csee.swplus.mileage.archive.project.mapper;  // service와 같은 레벨의 mapper 패키지

import com.csee.swplus.mileage.archive.project.dto.AllProjectsEntityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMapper {
    List<AllProjectsEntityDto> findAllProjects(@Param("studentId") String studentId);
}
