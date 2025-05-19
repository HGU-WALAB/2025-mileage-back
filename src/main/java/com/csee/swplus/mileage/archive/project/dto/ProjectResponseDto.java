package com.csee.swplus.mileage.archive.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectResponseDto {
    private int projectId;
    private String name;
    private String role;
    private String description;
    private String content;
    private String achievement;
    private String github_link;
    private String blog_link;
    private String deployed_link;
    private LocalDate start_date;
    private LocalDate end_date;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String thumbnail;
    private List<String> techStack;
}