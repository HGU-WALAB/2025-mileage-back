package com.csee.swplus.mileage.scholarship.dto;

import com.csee.swplus.mileage.scholarship.domain.Scholarship;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipResponseDto {
    private Integer isApply;  // Changed to camelCase

    public static ScholarshipResponseDto from(Scholarship scholarship) {
        return new ScholarshipResponseDto(scholarship.getIsApply());
    }
}