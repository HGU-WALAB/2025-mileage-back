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
    private int capabilityPoint;
}
