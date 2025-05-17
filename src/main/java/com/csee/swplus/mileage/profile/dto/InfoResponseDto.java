package com.csee.swplus.mileage.profile.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

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
