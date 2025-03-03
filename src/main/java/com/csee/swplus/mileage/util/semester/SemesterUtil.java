package com.csee.swplus.mileage.util.semester;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SemesterUtil {
    //    [피드백 반영 전 코드] 현재 학기 직접 계산하는 메서드
//    public static String getCurrentSemester() {
//        LocalDate now = LocalDate.now();
//        int year = now.getYear();
//        Month month = now.getMonth();
//
//        String semester = (month.getValue() <= 6) ? "01" : "02";
//        return year + "-" + semester;
//    }

    //    [피드백 반영 후 코드] 디비에서 현재 학기 정보 불러옴 -> SemesterRepository
    private final SemesterRepository semesterRepository;

    public String getCurrentSemester() {
        return semesterRepository.findFirstByOrderByIdDesc()
                .map(Semester::getCurrentSemester)
                .orElse("0000-00"); // 현재 학기 값이 없을 경우 기본값 설정
    }
}
