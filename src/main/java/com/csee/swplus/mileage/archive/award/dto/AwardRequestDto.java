package com.csee.swplus.mileage.archive.award.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AwardRequestDto {
    private LocalDate awardDate;
    private String awardYear;
    private String awardType;
    private String contestName;
    private String awardName;
    private String organization;
}