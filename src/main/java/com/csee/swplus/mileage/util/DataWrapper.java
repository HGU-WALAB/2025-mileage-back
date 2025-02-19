package com.csee.swplus.mileage.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DataWrapper {
    private List<?> data;
}
