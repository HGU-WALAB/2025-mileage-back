package com.csee.swplus.mileage.etcSubitem.service;

import com.csee.swplus.mileage.etcSubitem.domain.EtcSubitem;
import com.csee.swplus.mileage.util.DataWrapper;
import com.csee.swplus.mileage.etcSubitem.dto.StudentInputSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.dto.EtcSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.mapper.EtcSubitemMapper;
import com.csee.swplus.mileage.etcSubitem.repository.EtcSubitemRepository;
import com.csee.swplus.mileage.util.SemesterUtil;
import com.csee.swplus.mileage.etcSubitem.file.FileService;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor // not null 또는 final 인 필드를 받는 생성자
@Slf4j
public class EtcSubitemService {
    private final EtcSubitemMapper etcSubitemMapper;
    private final EtcSubitemRepository etcSubitemRepository;

    public DataWrapper getStudentInputSubitems() {
        String currentSemester = SemesterUtil.getCurrentSemester();
        log.info("📝 getCurrentSemester 결과 - current semester: " + currentSemester);
        List<StudentInputSubitemResponseDto> res = etcSubitemMapper.findAllStudentInputSubitems(currentSemester);
        log.info("📝 findAllStudentInputSubitems 결과 - res: {}", res);
        return new DataWrapper(res);
    }

    public DataWrapper getEtcSubitems(int studentId) {
        String currentSemester = SemesterUtil.getCurrentSemester();
        log.info("📝 getCurrentSemester 결과 - current semester: " + currentSemester);
        List<EtcSubitemResponseDto> res = etcSubitemMapper.findAllEtcSubitems(studentId, currentSemester);
        log.info("📝 getRequestedEtcSubitems 결과 - res: {}", res);
        return new DataWrapper(res);
    }

    @Transactional
    public MessageResponseDto postEtcSubitems(int studentId, String semester, String description1, String description2, int subitemId, String snum, String sname, MultipartFile file) {
        try {
//            1. EtcSubitem 엔티티 생성 및 데이터 설정
            EtcSubitem etcSubitem = new EtcSubitem();
//            앞단에서 전달 받는 값
            etcSubitem.setSemester(semester);
            etcSubitem.setSubitemId(subitemId);
            etcSubitem.setSnum(snum);
            etcSubitem.setSname(sname);
            etcSubitem.setDescription1(description1);
            etcSubitem.setDescription2(description2);

//           고정적인 값
            etcSubitem.setCategoryId(240);
            etcSubitem.setValue(1);
            etcSubitem.setExtraPoint(0);

//            마일리지 포인트
            int mPoint = etcSubitemMapper.getMPoint(subitemId);
            etcSubitem.setMPoint(mPoint);

//            2. 파일 처리
            FileService fileService = new FileService();
            String fileUrl = fileService.saveFile(file);
            etcSubitem.setDescription2(fileUrl);

//            3. DB에 저장
            etcSubitemRepository.save(etcSubitem);

//            4. 성공 메세지 반환
            return new MessageResponseDto("기타 항목이 등록되었습니다.");
        } catch (Exception e) {
            log.error("⚠️ 마일리지 신청 중 오류 발생: ", e);
            throw new RuntimeException("기타 항목 신청 처리 도중 오류가 발생했습니다.");
        }
    }
}