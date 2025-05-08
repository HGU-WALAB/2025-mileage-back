package com.csee.swplus.mileage.award.service;

import com.csee.swplus.mileage.award.dto.AwardRequestDto;
import com.csee.swplus.mileage.award.dto.AwardResponseDto;
import com.csee.swplus.mileage.award.mapper.AwardMapper;
import com.csee.swplus.mileage.award.repository.AwardRepository;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // not null 또는 final 인 필드를 받는 생성자
@Slf4j
public class AwardService {
    private final AwardMapper awardMapper;
    private final AwardRepository awardRepository;

    public List<AwardResponseDto> getAwards(String studentId) {
        return awardMapper.findAllAwards(studentId);
    }

    public MessageResponseDto postAward(AwardRequestDto awardRequestDto) {
        return new MessageResponseDto("교외 수상 내역이 등록되었습니다.");
    }
}