package com.csee.swplus.mileage.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilestonePointResponseDto {
    private int capabilityId;
    private String capabilityName;
    private int milestoneCount;     // 해당 역량 중 학생이 참여한 활동(subitem)의 개수
}
