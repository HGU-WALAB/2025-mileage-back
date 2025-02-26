package com.csee.swplus.mileage.auth.service;

import com.csee.swplus.mileage.setting.entity.SwManagerSetting;
import com.csee.swplus.mileage.setting.repository.SwManagerSettingRepository;
import com.csee.swplus.mileage.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.auth.controller.response.LoginResponse;
import com.csee.swplus.mileage.auth.dto.AuthDto;
import com.csee.swplus.mileage.auth.exception.DoNotExistException;
import com.csee.swplus.mileage.auth.util.JwtUtil;
import com.csee.swplus.mileage.user.entity.Users;
import com.csee.swplus.mileage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final SwManagerSettingRepository swManagerSettingRepository;
    private final UserService userService;

    @Value("${custom.jwt.secret}")
    private String SECRET_KEY;

    public Users getLoginUser(String uniqueId) {
        return (Users) userRepository
                .findByUniqueId(uniqueId)
                .orElseThrow(() -> new DoNotExistException("해당 유저가 없습니다."));
    }


    @Transactional
    public AuthDto login(AuthDto dto) {
        log.info("Login attempt with AuthDto: {}", dto);  // 이 부분에서 AuthDto 값 확인

        Optional<Users> user = userRepository.findByUniqueId(dto.getStudentId());

        String currentSemester = swManagerSettingRepository.findById(2L)
                .map(SwManagerSetting::getCurrentSemester)
                .orElse(null); // 없으면 null 반환
        log.info("SwManagerSetting에서 가져온 currentSemester 값: {}", currentSemester);

        // SECRET_KEY를 Key 타입으로 변환
        Key key = JwtUtil.getSigningKey(SECRET_KEY);

        if (!user.isPresent()) {
            Users newUser = Users.from(dto);
            newUser.increaseLoginCount(); // 로그인 횟수 증가
            userRepository.save(newUser);

            String studentType = userService.getUserInfo(newUser.getUniqueId()).getStudentType();

            // 토큰 생성 후 LoginResponse 생성
            LoginResponse response = LoginResponse.from(
                    AuthDto.builder()
                            .token(JwtUtil.createToken(newUser.getUniqueId(), newUser.getName(), newUser.getEmail(), key))
                            .studentId(newUser.getUniqueId())
                            .studentName(newUser.getName())
                            .studentEmail(newUser.getEmail())
                            .department(newUser.getDepartment())
                            .major1(newUser.getMajor1())
                            .major2(newUser.getMajor2())
                            .grade(newUser.getGrade())
                            .term(newUser.getSemester())
                            .studentType(studentType)
                            .build()
            ).withCurrentSemester(currentSemester);

            log.info("Login successful. Response: {}", response); // response 값 로그 출력

            return AuthDto.builder()
                    .token(response.getToken())
                    .studentId(response.getStudentId())  // uniqueId 추가
                    .studentName(response.getStudentName())          // name 추가
                    .studentEmail(response.getStudentEmail())        // email 추가
                    .department(response.getDepartment())  // department 추가
                    .major1(response.getMajor1())      // major1 추가
                    .major2(response.getMajor2())      // major2 추가
                    .grade(response.getGrade())        // grade 추가
                    .term(response.getTerm())  // semester 추가
                    .currentSemester(response.getCurrentSemester())
                    .studentType(response.getStudentType())
                    .build();
        } else {
            user.get().increaseLoginCount();
            userRepository.save(user.get());

            log.info("Received AuthDto in AuthService: {}", dto);

            String studentType = userService.getUserInfo(user.get().getUniqueId()).getStudentType();

            // 토큰 생성 후 LoginResponse 생성
            LoginResponse response = LoginResponse.from(
                    AuthDto.builder()
                            .token(JwtUtil.createToken(user.get().getUniqueId(), user.get().getName(), user.get().getEmail(), key))
                            .studentId(user.get().getUniqueId())
                            .studentName(user.get().getName())
                            .studentEmail(user.get().getEmail())
                            .department(user.get().getDepartment())
                            .major1(user.get().getMajor1())
                            .major2(user.get().getMajor2())
                            .grade(user.get().getGrade())
                            .term(user.get().getSemester())
                            .studentType(studentType)
                            .build()
            ).withCurrentSemester(currentSemester);

            log.info("Login successful. Responselalalala: {}", response); // response 값 로그 출력

            return AuthDto.builder()
                    .token(response.getToken())
                    .studentId(response.getStudentId())  // uniqueId 추가
                    .studentName(response.getStudentName())          // name 추가
                    .studentEmail(response.getStudentEmail())        // email 추가
                    .department(response.getDepartment())  // department 추가
                    .major1(response.getMajor1())      // major1 추가
                    .major2(response.getMajor2())      // major2 추가
                    .grade(response.getGrade())        // grade 추가
                    .term(response.getTerm())  // semester 추가
                    .currentSemester(response.getCurrentSemester())
                    .studentType(response.getStudentType())
                    .build();
        }
    }
}