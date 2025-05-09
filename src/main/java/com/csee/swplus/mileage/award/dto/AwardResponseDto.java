package com.csee.swplus.mileage.award.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AwardResponseDto {
    private int awardId;
    private String awardYear;
    private LocalDate awardDate;
    private String contestName;
    private String awardName;
    private String awardType;
    private String organization;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}

