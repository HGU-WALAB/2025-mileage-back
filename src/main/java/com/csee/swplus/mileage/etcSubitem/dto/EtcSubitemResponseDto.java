package com.csee.swplus.mileage.etcSubitem.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class EtcSubitemResponseDto {
    private Long subitemId;
    private String subitemName;
    private Long categoryId;
    private String categoryName;
    private String semester;
}

