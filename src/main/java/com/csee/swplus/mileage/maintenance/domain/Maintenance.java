package com.csee.swplus.mileage.maintenance.domain;

import com.csee.swplus.mileage.base.entity.BaseTime;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "_sw_maintenance")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance extends BaseTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_maintenance_mode", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isMaintenanceMode;

    @Column(name = "maintenance_message", length = 500)
    private String maintenanceMessage;

    @Column(name = "estimated_time", length = 100)
    private String estimatedTime;

    @Column(name = "allowed_emails", columnDefinition = "TEXT")
    private String allowedEmails; // JSON string of allowed emails

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isActive;

    public Maintenance(Boolean isMaintenanceMode, String maintenanceMessage, String estimatedTime, String allowedEmails) {
        this.isMaintenanceMode = isMaintenanceMode;
        this.maintenanceMessage = maintenanceMessage;
        this.estimatedTime = estimatedTime;
        this.allowedEmails = allowedEmails;
        this.isActive = true;
    }
}
