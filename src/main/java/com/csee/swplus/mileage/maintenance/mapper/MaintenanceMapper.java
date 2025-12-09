package com.csee.swplus.mileage.maintenance.mapper;

import com.csee.swplus.mileage.maintenance.domain.Maintenance;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceSettingsRequest;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceSettingsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MaintenanceMapper {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public Maintenance toEntity(MaintenanceSettingsRequest request) {
        try {
            String allowedEmailsJson = request.getAllowedEmails() != null ? 
                objectMapper.writeValueAsString(request.getAllowedEmails()) : "[]";
            
            return Maintenance.builder()
                    .isMaintenanceMode(request.getIsMaintenanceMode())
                    .maintenanceMessage(request.getMaintenanceMessage())
                    .estimatedTime(request.getEstimatedTime())
                    .allowedEmails(allowedEmailsJson)
                    .isActive(true)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert allowed emails to JSON", e);
        }
    }
    
    public MaintenanceSettingsResponse toResponse(Maintenance maintenance) {
        try {
            List<String> allowedEmails = maintenance.getAllowedEmails() != null ? 
                objectMapper.readValue(maintenance.getAllowedEmails(), new TypeReference<List<String>>() {}) : 
                java.util.Collections.emptyList();
            
            return MaintenanceSettingsResponse.builder()
                    .id(maintenance.getId())
                    .isMaintenanceMode(maintenance.getIsMaintenanceMode())
                    .maintenanceMessage(maintenance.getMaintenanceMessage())
                    .estimatedTime(maintenance.getEstimatedTime())
                    .allowedEmails(allowedEmails)
                    .isActive(maintenance.getIsActive())
                    .regdate(maintenance.getRegdate() != null ? maintenance.getRegdate().format(formatter) : null)
                    .moddate(maintenance.getModdate() != null ? maintenance.getModdate().format(formatter) : null)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert allowed emails from JSON", e);
        }
    }
    
    public void updateEntity(Maintenance entity, MaintenanceSettingsRequest request) {
        try {
            if (request.getIsMaintenanceMode() != null) {
                entity.setIsMaintenanceMode(request.getIsMaintenanceMode());
            }
            if (request.getMaintenanceMessage() != null) {
                entity.setMaintenanceMessage(request.getMaintenanceMessage());
            }
            if (request.getEstimatedTime() != null) {
                entity.setEstimatedTime(request.getEstimatedTime());
            }
            if (request.getAllowedEmails() != null) {
                entity.setAllowedEmails(objectMapper.writeValueAsString(request.getAllowedEmails()));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert allowed emails to JSON", e);
        }
    }
}
