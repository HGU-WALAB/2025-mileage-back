package com.csee.swplus.mileage.scholarship.mapper;  // service와 같은 레벨의 mapper 패키지

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface ScholarshipMapper {
    // [기존 코드] 장학금 신청 시 _sw_student 테이블의 is_apply 와 apply_date 컬럼 update
//    int createApplication(@Param("studentId") int studentId,
//                          @Param("applyDate") LocalDateTime applyDate,
//                          @Param("isChecked") int isChecked);

    // [피드백 수용 코드] 장학금 신청 테이블 존재
    int createApplication(@Param("studentId") int studentId,
                          @Param("isChecked") int isChecked,
                          @Param("semester") String semester);
}
