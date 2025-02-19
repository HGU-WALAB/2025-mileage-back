package com.csee.swplus.mileage.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MilestoneSemesterResponseDto {
    private int capabilityId;
    private String capabilityName;
    private String semester;
    private int capabilityPoint;
}
