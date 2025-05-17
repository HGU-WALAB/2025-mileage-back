package com.csee.swplus.mileage.profile.not_shared.service;

import com.csee.swplus.mileage.profile.not_shared.dto.TeckStackResponseDto;
import com.csee.swplus.mileage.profile.not_shared.mapper.TeckStackMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileTeckStackService {
    private final TeckStackMapper teckStackMapper;
    public TeckStackResponseDto getTeckStack(String studentId) { return teckStackMapper.findTeckStackByUserId(studentId);}
}