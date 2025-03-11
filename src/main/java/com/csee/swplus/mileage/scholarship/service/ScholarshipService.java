package com.csee.swplus.mileage.scholarship.service;

import com.csee.swplus.mileage.scholarship.mapper.ScholarshipMapper;
import com.csee.swplus.mileage.scholarship.repository.ScholarshipRepository;
import com.csee.swplus.mileage.util.semester.SemesterUtil;
import com.csee.swplus.mileage.scholarship.domain.Scholarship;
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
    private final ScholarshipRepository scholarshipRepository;

    // [기존 코드] 장학금 신청 시 _sw_student 테이블의 is_apply 와 apply_date 컬럼 update
//    @Transactional
//    public void applyScholarship(int studentId, boolean isAgree) {
//        LocalDateTime now = LocalDateTime.now();
//        int isChecked = isAgree ? 1 : 0;
//
//        log.info("📌 applyScholarship 실행 - studentId: {}, isAgree: {}, isChecked: {}, applyDate: {}",
//                studentId, isAgree, isChecked, now);
//
//        int updatedRows = scholarshipMapper.createApplication(studentId, now, isChecked);
//
//        log.info("📝 createApplication 결과 - updatedRows: {}", updatedRows);
//
//        if (updatedRows == 0) {
//            log.warn("⚠️ 이미 신청된 학생이거나 존재하지 않는 학생 - studentId: {}", studentId);
//            throw new IllegalStateException("이미 신청된 학생이거나 존재하지 않는 학생입니다.");
//        }
//    }

    // [피드백 수용 후 코드] 장학금 신청 테이블 존재

    @Transactional
    public void applyScholarship(String studentId, boolean isAgree) {
        int isChecked = isAgree ? 1 : 0;
        String semester = semesterUtil.getCurrentSemester();

        log.info("📌 applyScholarship 실행 - studentId: {}, isAgree: {}, isChecked: {}, semester: {}",
                studentId, isAgree, isChecked, semester);

        int updatedRows = scholarshipMapper.createApplication(studentId, isChecked, semester);

        log.info("📝 createApplication 결과 - updatedRows: {}", updatedRows);

        if (updatedRows == 0) {
            log.warn("⚠️ 이미 신청된 학생이거나 존재하지 않는 학생 - studentId: {}", studentId);
            throw new IllegalStateException("이미 신청된 학생이거나 존재하지 않는 학생입니다.");
        }
    }

    public Integer getIsApplyStatus(String studentId) {
        return scholarshipRepository.findByStudentId(studentId)
                .map(Scholarship::getIsApply) // ✅ 올바른 getter 사용
                .orElseThrow(() -> new RuntimeException("해당 학번을 가진 학생이 존재하지 않습니다."));
    }
}