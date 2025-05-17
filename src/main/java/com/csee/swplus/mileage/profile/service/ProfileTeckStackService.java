package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.profile.domain.TechStack;
import com.csee.swplus.mileage.profile.dto.TeckStackRequestDto;
import com.csee.swplus.mileage.profile.dto.TeckStackResponseDto;
import com.csee.swplus.mileage.profile.mapper.TeckStackMapper;
import com.csee.swplus.mileage.profile.repository.ProfileTeckStackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileTeckStackService {
    private final TeckStackMapper teckStackMapper;
    private final ProfileTeckStackRepository teckStackRepository;

    public TeckStackResponseDto getTeckStack(String studentId) {
        teckStackMapper.insertIfNotExists(studentId);
        return teckStackMapper.findTeckStackByUserId(studentId);
    }

    @Transactional
    public void patchTeckStack(String studentId, TeckStackRequestDto teckStackRequestDto) {
        TechStack stack = teckStackRepository.findBySnum(studentId).orElseThrow(() -> new RuntimeException("Not found"));

        if (teckStackRequestDto.getStacks() != null) stack.setStacks(teckStackRequestDto.getStacks());
    }
}