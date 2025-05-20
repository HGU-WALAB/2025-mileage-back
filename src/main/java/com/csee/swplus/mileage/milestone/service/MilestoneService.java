package com.csee.swplus.mileage.milestone.service;

import com.csee.swplus.mileage.archive.project.mapper.ProjectMapper;
import com.csee.swplus.mileage.milestone.dto.response.*;
import com.csee.swplus.mileage.milestone.mapper.MilestoneMapper;
import com.csee.swplus.mileage.profile.dto.MileageCountResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MilestoneService {
    private final MilestoneMapper milestoneMapper;
    private final ProjectMapper projectMapper;

    public List<MilestoneResponseDto> getMilestoneCapabilities() {
        List<MilestoneResponseDto> res = milestoneMapper.findAllMilestoneCapability();
        log.info("📝 findAllMilestoneCapability 결과 - res: {}", res);
        return res;
    }

    public List<MilestonePointResponseDto> getMilestonePoint(int studentId) {
        List<MilestonePointResponseDto> res = milestoneMapper.findAllMilestonePoint(studentId);
        log.info("📝 findAllMilestonePoint 결과 - res: {}", res);
        return res;
    }

    public List<MPResponseDto> getFilteredAverageMilestonePoint(String term, String entryYear, String major) {
        return milestoneMapper.findFilteredAverageMilestonePoint(term, entryYear, major);
    }

//    public List<MilestoneSemesterResponseDto> getMilestoneSemester(int studentId) {
//        List<MilestoneSemesterResponseDto> res = milestoneMapper.findEachMilestoneBySemester(studentId);
//        log.info("📝 findEachMilestoneBySemester 결과 - res: {}", res);
//        return res;
//    }

    public List<MilestoneSemesterTotalPointResponseDto> getTotalMilestoneSemester(int studentId) {
        List<MilestoneSemesterTotalPointResponseDto> res = milestoneMapper.findAllMilestoneBySemester(studentId);
        log.info("📝 findAllMilestoneBySemester 결과 - res: {}", res);
        return res;
    }
    public SuggestItemResponseDto getSuggestionsForStudent(String studentId) {
        try {
            List<Map<String, Object>> results = milestoneMapper.findSuggestItemByUserId(studentId);

            if (results != null && !results.isEmpty()) {
                // Extract data from the results
                String capabilityName = (String) results.get(0).get("capabilityName");
                List<String> suggestions = new ArrayList<>();

                for (Map<String, Object> result : results) {
                    suggestions.add((String) result.get("subitemName"));
                }

                // Create and populate response DTO
                SuggestItemResponseDto responseDto = new SuggestItemResponseDto();
                responseDto.setCapabilityName(capabilityName);
                responseDto.setSuggestion(suggestions);

                log.info("📝 getSuggestionsForStudent 결과 - capability: {}, suggestions: {}",
                        capabilityName, suggestions);

                return responseDto;
            } else {
                // No results found - provide fallback suggestions
                log.warn("📝 getSuggestionsForStudent - 학생에 대한 추천 항목을 찾을 수 없습니다: {}", studentId);

                // Get the lowest capability for this student - simplified approach
                List<MilestonePointResponseDto> milestonePoints = getMilestonePoint(Integer.parseInt(studentId));

                // Sort by completion rate (milestoneCount/totalMilestoneCount)
                milestonePoints.sort(Comparator.comparingDouble(point ->
                        point.getTotalMilestoneCount() > 0 ?
                                (double) point.getMilestoneCount() / point.getTotalMilestoneCount() :
                                1.0));

                // Create fallback response
                SuggestItemResponseDto fallbackResponse = new SuggestItemResponseDto();

                if (!milestonePoints.isEmpty()) {
                    // Use the lowest capability name
                    fallbackResponse.setCapabilityName(milestonePoints.get(0).getCapabilityName());
                    fallbackResponse.setSuggestion(Arrays.asList(
                            "SW 관련 프로젝트 참여",
                            "학과 관련 활동 참가",
                            "SW 역량 관련 교육 수강"
                    ));
                } else {
                    // Absolute fallback if no capability data is available
                    fallbackResponse.setCapabilityName("SW 개발 역량");
                    fallbackResponse.setSuggestion(Arrays.asList(
                            "SW 관련 프로젝트 참여",
                            "프로그래밍 경진대회 참가",
                            "SW 역량 관련 교육 수강"
                    ));
                }

                return fallbackResponse;
            }
        } catch (Exception e) {
            log.error("📝 getSuggestionsForStudent - 오류 발생: {}", e.getMessage(), e);

            // Return default suggestions in case of any error
            SuggestItemResponseDto errorResponse = new SuggestItemResponseDto();
            errorResponse.setCapabilityName("SW 개발 역량");
            errorResponse.setSuggestion(Arrays.asList(
                    "SW 관련 프로젝트 참여",
                    "프로그래밍 경진대회 참가",
                    "SW 역량 관련 교육 수강"
            ));

            return errorResponse;
        }
    }

}
