package com.csee.swplus.mileage.maintenance.repository;

import com.csee.swplus.mileage.maintenance.domain.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    
    // Find the active maintenance configuration
    Optional<Maintenance> findByIsActiveTrue();
    
    // Check if maintenance mode is currently active
    boolean existsByIsMaintenanceModeTrueAndIsActiveTrue();
}
