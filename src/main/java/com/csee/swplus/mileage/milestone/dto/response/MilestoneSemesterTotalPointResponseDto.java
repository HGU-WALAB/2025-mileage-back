package com.csee.swplus.mileage.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneSemesterTotalPointResponseDto {
    private String semester;
    private int totalCapabilityPoint;
}
