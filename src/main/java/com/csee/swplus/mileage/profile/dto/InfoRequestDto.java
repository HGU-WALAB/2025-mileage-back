package com.csee.swplus.mileage.profile.dto;

import lombok.Data;

@Data
public class InfoRequestDto {
    private String profile_image_url;
    private String self_description;
    private String job;
    private String github_link;
    private String instagram_link;
    private String blog_link;
    private String linkedin_link;
}