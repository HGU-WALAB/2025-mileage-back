package com.csee.swplus.mileage.profile.not_shared.service;

import com.csee.swplus.mileage.profile.not_shared.domain.Info;
import com.csee.swplus.mileage.profile.not_shared.dto.InfoResponseDto;
import com.csee.swplus.mileage.profile.not_shared.mapper.InfoMapper;
import com.csee.swplus.mileage.profile.not_shared.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileInfoService {
    private final InfoMapper infoMapper;

    public InfoResponseDto getInfo(String studentId) {
        return infoMapper.findInfoByUserId(studentId);
    }

    // patch code need to be add
}
