package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.profile.domain.TechStack;
import com.csee.swplus.mileage.profile.dto.TechStackRequestDto;
import com.csee.swplus.mileage.profile.dto.TechStackResponseDto;
import com.csee.swplus.mileage.profile.mapper.TechStackMapper;
import com.csee.swplus.mileage.profile.repository.ProfileTeckStackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileTeckStackService {
    private final TechStackMapper techStackMapper;
    private final ProfileTeckStackRepository teckStackRepository;

    public TechStackResponseDto getTechStack(String studentId) {
        techStackMapper.insertIfNotExists(studentId);
        String stackStr = techStackMapper.findTechStackByUserId(studentId);
        List<String> stackList = (stackStr == null || stackStr.isBlank())
                ? List.of()
                : Arrays.asList(stackStr.split(","));
        return new TechStackResponseDto(stackList);
    }

    @Transactional
    public void patchTeckStack(String studentId, TechStackRequestDto techStackRequestDto) {
        TechStack stack = teckStackRepository.findBySnum(studentId).orElseThrow(() -> new RuntimeException("Not found"));

        if (techStackRequestDto.getTeckStack() != null) stack.setStacks(techStackRequestDto.getTeckStack());
    }
}