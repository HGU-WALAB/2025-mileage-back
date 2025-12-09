package com.csee.swplus.mileage.maintenance.dto;

import lombok.Data;

@Data
public class MaintenanceStatusRequest {
    private boolean maintenanceMode;
    private String message;
    private String estimatedTime;
}
