package com.csee.swplus.mileage.profile.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class InfoResponseDto {
    private String studentId;
    private String studentName;
    private String profile_image_url;
    private String self_description;
    private String job;
    private String github_link;
    private String instagram_link;
    private String blog_link;
    private String linkedin_link;
}
