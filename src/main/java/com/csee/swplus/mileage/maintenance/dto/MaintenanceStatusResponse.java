package com.csee.swplus.mileage.maintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaintenanceStatusResponse {
    private boolean maintenanceMode;
    private String message;
    private String estimatedTime;
    private boolean isAllowedUser;
}
