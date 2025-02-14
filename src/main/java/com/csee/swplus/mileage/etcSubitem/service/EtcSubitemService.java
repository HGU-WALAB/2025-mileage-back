package com.csee.swplus.mileage.etcSubitem.service;

import com.csee.swplus.mileage.etcSubitem.dto.DataWrapper;
import com.csee.swplus.mileage.etcSubitem.dto.EtcSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.dto.RequestedEtcSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.mapper.EtcSubitemMapper;
import com.csee.swplus.mileage.util.SemesterUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // not null 또는 final 인 필드를 받는 생성자
@Slf4j
public class EtcSubitemService {
    private final EtcSubitemMapper etcSubitemMapper;

    @Transactional
    public DataWrapper getStudentInputSubitems() {
        String currentSemester = SemesterUtil.getCurrentSemester();
        log.info("📝 getCurrentSemester 결과 - current semester: " + currentSemester);
        List<EtcSubitemResponseDto> res = etcSubitemMapper.findAllStudentInputSubitems(currentSemester);
        log.info("📝 findAllStudentInputSubitems 결과 - res: {}", res);
        return new DataWrapper(res);
    }

    @Transactional
    public DataWrapper getRequestedEtcSubitems(int studentId) {
        String currentSemester = SemesterUtil.getCurrentSemester();
        List<RequestedEtcSubitemResponseDto> res = etcSubitemMapper.findAllRequestedEtcSubitems(studentId, currentSemester);
        log.info("📝 getRequestedEtcSubitems 결과 - res: {}", res);
        return new DataWrapper(res);
    }
}