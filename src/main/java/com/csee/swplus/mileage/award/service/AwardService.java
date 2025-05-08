package com.csee.swplus.mileage.award.service;

import com.csee.swplus.mileage.award.domain.Award;
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

    public MessageResponseDto postAward(String studentId, AwardRequestDto awardRequestDto) {
        try {
            Award award = new Award(
                    studentId,
                    awardRequestDto.getAwardDate(),
                    awardRequestDto.getAwardYear(),
                    awardRequestDto.getContestName(),
                    awardRequestDto.getAwardName(),
                    awardRequestDto.getAwardType(),
                    awardRequestDto.getOrganization()
            );

            awardRepository.save(award);
            return new MessageResponseDto("수상 내역이 등록되었습니다.");
        } catch (Exception e) {
            log.error("⚠️ 수상 내역 등록 중 오류 발생: ", e);
            throw new RuntimeException("수상 내역 등록 중 오류가 발생했습니다.");
        }
    }

    public MessageResponseDto patchAward(String studentId, int awardId, AwardRequestDto awardRequestDto) {
        try {
            Award award = awardRepository.findById(awardId)
                    .orElseThrow(() -> new RuntimeException("해당 수상 내역을 찾을 수 없습니다."));

            award.setAwardDate(awardRequestDto.getAwardDate());
            award.setAwardYear(awardRequestDto.getAwardYear());
            award.setContestName(awardRequestDto.getContestName());
            award.setAwardName(awardRequestDto.getAwardName());
            award.setAwardType(awardRequestDto.getAwardType());
            award.setOrganization(awardRequestDto.getOrganization());

            awardRepository.save(award);
            return new MessageResponseDto("수상 내역이 수정되었습니다.");
        } catch (Exception e) {
            log.error("⚠️ 수상 내역 수정 중 오류 발생: ", e);
            throw new RuntimeException("수상 내역 수정 중 오류가 발생했습니다.");
        }
    }

    public MessageResponseDto deleteAward(String studentId, int awardId) {
        try {
            awardRepository.deleteById(awardId);

            return new MessageResponseDto("수상 내역이 삭제되었습니다.");
        } catch (Exception e) {
            log.error("⚠️ 수상 내역 삭제 중 오류 발생: ", e);
            throw new RuntimeException("수상 내역 삭제 중 오류가 발생했습니다.");
        }
    }
}