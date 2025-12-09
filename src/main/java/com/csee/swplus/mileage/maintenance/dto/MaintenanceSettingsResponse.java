package com.csee.swplus.mileage.maintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceSettingsResponse {
    private Long id;
    private Boolean isMaintenanceMode;
    private String maintenanceMessage;
    private String estimatedTime;
    private List<String> allowedEmails;
    private Boolean isActive;
    private String regdate;
    private String moddate;
}
