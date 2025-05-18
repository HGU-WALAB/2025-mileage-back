package com.csee.swplus.mileage.archive.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AllProjectsResponseDto {
    private int projectId;
    private String name;
    private String role;
    private LocalDate start_date;
    private LocalDate end_date;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String thumbnail;
    private List<String> techStack;   // raw comma-separated string
}