package com.csee.swplus.mileage.profile.service;

import com.csee.swplus.mileage.profile.domain.TechStack;
import com.csee.swplus.mileage.profile.dto.TechStackRequestDto;
import com.csee.swplus.mileage.profile.dto.TechStackResponseDto;
import com.csee.swplus.mileage.profile.mapper.TechStackMapper;
import com.csee.swplus.mileage.profile.repository.ProfileTechStackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileTechStackService {
    private final TechStackMapper techStackMapper;
    private final ProfileTechStackRepository techStackRepository;

    public TechStackResponseDto getTechStack(String studentId) {
        techStackMapper.insertIfNotExists(studentId);
        String stackStr = techStackMapper.findTechStackByUserId(studentId);
        List<String> stackList;
        if (stackStr == null || stackStr.isEmpty()) {
            stackList = Collections.emptyList();
        } else {
            stackList = Arrays.stream(stackStr.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }

        return new TechStackResponseDto(stackList);
    }

    @Transactional
    public void patchTechStack(String studentId, TechStackRequestDto techStackRequestDto) {
        TechStack stack = techStackRepository.findBySnum(studentId).orElseThrow(() -> new RuntimeException("Not found"));

        if (techStackRequestDto.getTechStack() != null) {
            String joined = String.join(",", techStackRequestDto.getTechStack());
            stack.setStacks(joined);
        }
    }
}