package com.csee.swplus.mileage.etcSubitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DataWrapper {
    private List<EtcSubitemResponseDto> data;
}
