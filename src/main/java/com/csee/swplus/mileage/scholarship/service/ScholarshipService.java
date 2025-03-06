package com.csee.swplus.mileage.scholarship.service;

import com.csee.swplus.mileage.scholarship.mapper.ScholarshipMapper;
import com.csee.swplus.mileage.util.semester.SemesterUtil;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // not null 또는 final 인 필드를 받는 생성자
@Slf4j
public class ScholarshipService {
    private final ScholarshipMapper scholarshipMapper;
    private final SemesterUtil semesterUtil;

    @Transactional
    public void applyScholarship(String studentId, boolean isAgree) {
        int isChecked = isAgree ? 1 : 0;
        String semester = semesterUtil.getCurrentSemester();

        log.info("📌 applyScholarship-createApplication 실행 - studentId: {}, isAgree: {}, isChecked: {}, semester: {}",
                studentId, isAgree, isChecked, semester);

        int scholarshipUpdatedRows = scholarshipMapper.createApplication(studentId, isChecked, semester);

        log.info("📝 createApplication 결과 - updatedRows: {}", scholarshipUpdatedRows);

        LocalDateTime now = LocalDateTime.now();

        log.info("📌 applyScholarship-updateStudentApplicationStatus 실행 - studentId: {}, isAgree: {}, isChecked: {}, applyDate: {}",
                studentId, isAgree, isChecked, now);

        int studentUpdatedRows = scholarshipMapper.updateStudentApplicationStatus(studentId, now, isChecked);

        log.info("📝 updateStudentApplicationStatus 결과 - updatedRows: {}", studentUpdatedRows);

        if (scholarshipUpdatedRows == 0 || studentUpdatedRows == 0) {
            log.warn("⚠️ 이미 신청된 학생이거나 존재하지 않는 학생 - studentId: {}", studentId);
            throw new IllegalStateException("이미 신청된 학생이거나 존재하지 않는 학생입니다.");
        }
    }
}