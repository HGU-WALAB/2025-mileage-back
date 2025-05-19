package com.csee.swplus.mileage.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class TechStackResponseDto {
    private List<String> techStack;
}