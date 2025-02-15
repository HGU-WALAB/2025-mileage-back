package com.csee.swplus.mileage.scholarship.service;

import com.csee.swplus.mileage.scholarship.mapper.ScholarshipMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // not null 또는 final 인 필드를 받는 생성자
@Slf4j
public class ScholarshipService {
    private final ScholarshipMapper scholarshipMapper;

    @Transactional
    public void applyScholarship(int studentId, boolean isAgree) {
        LocalDateTime now = LocalDateTime.now();
        int isChecked = isAgree ? 1 : 0;

        log.info("📌 applyScholarship 실행 - studentId: {}, isAgree: {}, isChecked: {}, applyDate: {}",
                studentId, isAgree, isChecked, now);

        int updatedRows = scholarshipMapper.createApplication(studentId, now, isChecked);

        log.info("📝 createApplication 결과 - updatedRows: {}", updatedRows);

        if (updatedRows == 0) {
            log.warn("⚠️ 이미 신청된 학생이거나 존재하지 않는 학생 - studentId: {}", studentId);
            throw new IllegalStateException("이미 신청된 학생이거나 존재하지 않는 학생입니다.");
        }
    }
}