package com.csee.swplus.mileage.etcSubitem.mapper;  // service와 같은 레벨의 mapper 패키지

import com.csee.swplus.mileage.etcSubitem.dto.DataWrapper;
import com.csee.swplus.mileage.etcSubitem.dto.EtcSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.dto.RequestedEtcSubitemResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EtcSubitemMapper {
    List<EtcSubitemResponseDto> findAllStudentInputSubitems(@Param("currentSemester") String currentSemester);

    List<RequestedEtcSubitemResponseDto> findAllRequestedEtcSubitems(@Param("studentId") int studentId, @Param("currentSemester") String currentSemester);
}
