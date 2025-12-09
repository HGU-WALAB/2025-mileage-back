package com.csee.swplus.mileage.maintenance.dto;

import lombok.Data;
import java.util.List;

@Data
public class MaintenanceSettingsRequest {
    private Boolean isMaintenanceMode;
    private String maintenanceMessage;
    private String estimatedTime;
    private List<String> allowedEmails;
}
