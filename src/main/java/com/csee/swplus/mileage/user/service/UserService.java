package com.csee.swplus.mileage.user.service;

import com.csee.swplus.mileage.user.entity.StudentSchool;
import com.csee.swplus.mileage.user.mapper.StudentSchoolMapper;
import com.csee.swplus.mileage.user.repository.StudentSchoolRepository;
import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.user.controller.request.UserRequest;
import com.csee.swplus.mileage.user.controller.response.UserResponse;
import com.csee.swplus.mileage.user.entity.Users;
import com.csee.swplus.mileage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final StudentSchoolRepository studentSchoolRepository;
    private final StudentSchoolMapper studentSchoolMapper;

    // 유저 정보 조회
    public UserResponse getUserInfo(String studentId) {
        Users user = userRepository.findByUniqueId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 먼저 school 정보 저장 (없다면 새로 추가됨)
        saveUserSchoolInfo(studentId);

        // 저장된 StudentSchool에서 stype 가져오기
//        StudentSchool studentSchool = studentSchoolRepository
//                .findBySchoolAndMajor1AndMajor2(user.getDepartment(), user.getMajor1(), user.getMajor2())
//                .orElseThrow(() -> new RuntimeException("StudentSchool not found after saving"));
        List<StudentSchool> studentSchools = studentSchoolRepository
                .findBySchoolAndMajor1AndMajor2(user.getDepartment(), user.getMajor1(), user.getMajor2());
        if (studentSchools.isEmpty()) {
            throw new RuntimeException("StudentSchool not found after saving");
        }

        // 여러 개의 결과가 있을 경우 첫 번째 값 사용
        StudentSchool studentSchool = studentSchools.get(0);
        return UserResponse.from(user, studentSchool.getStype());
    }

    @Transactional
    public void saveUserSchoolInfo(String studentId) {
        Users user = userRepository.findByUniqueId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        Optional<StudentSchool> existingSchool = studentSchoolRepository
//                .findBySchoolAndMajor1AndMajor2(user.getDepartment(), user.getMajor1(), user.getMajor2());
//
//        if (existingSchool.isPresent()) {
//            log.info("StudentSchool already exists: {}", existingSchool.get());
//            return;
//        }

        List<StudentSchool> existingSchools = studentSchoolRepository
                .findBySchoolAndMajor1AndMajor2(user.getDepartment(), user.getMajor1(), user.getMajor2());

        if (!existingSchools.isEmpty()) {
            log.info("StudentSchool already exists: {}", existingSchools.get(0));
            return;
        }
        

        // 새로 저장
        StudentSchool schoolInfo = StudentSchool.from(user);
        // ✅ MyBatis Mapper 사용하여 `stype` 조회
        String stype = studentSchoolMapper.findStype(
                schoolInfo.getSchool(),
                schoolInfo.getMajor1(),
                schoolInfo.getMajor2()
        );

        // `stype`이 null일 경우 "기타"로 설정
        schoolInfo.setStype(stype != null ? stype : "기타");

        log.info("Saving new StudentSchool: school={}, major1={}, major2={}, stype={}",
                schoolInfo.getSchool(), schoolInfo.getMajor1(), schoolInfo.getMajor2(), schoolInfo.getStype());

        studentSchoolRepository.save(schoolInfo);
    }

//    private String determineStype(StudentSchool studentSchool) {
//        String school = studentSchool.getSchool();
//        String major1 = studentSchool.getMajor1();
//        String major2 = studentSchool.getMajor2();
//
//        if ("전산전자공학부".equals(school)) {
//            if ("AI·컴퓨터공학심화(60)".equals(major1) ||
//                    "전자공학심화(60)".equals(major1) ||
//                    "컴퓨터공학(33)".equals(major1) ||
//                    "컴퓨터공학(40)".equals(major1) ||
//                    "컴퓨터공학(45)".equals(major1) ||
//                    "전자공학(33)".equals(major1)
//            ) {}
//            return "전공";
//        }
//        if ("글로벌리더십학부".equals(school)) {
//            if ("AI·컴퓨터공학심화(60)".equals(major1)) {
//                return "1학년";
//            }
//        }
//        if ("컴퓨터공학(33)".equals(major2) ||
//                "컴퓨터공학(40)".equals(major2) ||
//                "컴퓨터공학(45)".equals(major2) ||
//                "IT(40)".equals(major2) ||
//                "ICT(45)".equals(major2) ||
//                "ACE(40)".equals(major2) ||
//                "DS(45)".equals(major2)
//        ) {
//            return "융합";
//        }
//        if ("IT(40)".equals(major1) ||
//                "ICT(45)".equals(major1) ||
//                "AI Convergence & Entrepreneurship(45)".equals(major1)
//        ) {
//            return "융합";
//        }
//        if (("생명공학(33)".equals(major1)) || ("경영(40)".equals(major1)) ||
//                ("경제(45)".equals(major1))){
//            if ("AI융합".equals(major2)) {
//                return "융합";
//            }
//        }
//        return "기타";
//    }

//    private static boolean isScholarsipAvailable() {
//        String type = determineStype();
//
//        if(type == "기타")
//            return false;
//        else
//            return true;
//    }
}
