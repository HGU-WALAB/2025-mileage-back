package com.csee.swplus.mileage.profile.not_shared.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InfoResponseDto {
    private String profileImageUrl;
    private String selfDescription;
    private String job;
    private String githubLink;
    private String instagramLink;
    private String blogLink;
    private String linkedinLink;
}
