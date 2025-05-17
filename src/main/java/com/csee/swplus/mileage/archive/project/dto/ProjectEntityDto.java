package com.csee.swplus.mileage.archive.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProjectEntityDto {
    private int projectId;
    private String name;
    private String role;
    private String description;
    private String content;
    private String achievement;
    private String githubLink;
    private String blogLink;
    private String deployedLink;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String thumbnail;
    private String techStack;   // raw comma-separated string
}