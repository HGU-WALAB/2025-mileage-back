package com.csee.swplus.mileage.maintenance.controller;

import com.csee.swplus.mileage.maintenance.dto.MaintenanceSettingsRequest;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceSettingsResponse;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceStatusRequest;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceStatusResponse;
import com.csee.swplus.mileage.maintenance.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
@Slf4j
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @GetMapping("/status")
    public ResponseEntity<MaintenanceStatusResponse> getMaintenanceStatus(
            @RequestParam(required = false) String email) {

        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("점검 상태 확인 요청 - 사용자: {}, 이메일: {}", currentUserId, email);

        MaintenanceStatusResponse response = maintenanceService.getMaintenanceStatus(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggle")
    public ResponseEntity<MaintenanceStatusResponse> toggleMaintenanceMode(
            @RequestBody MaintenanceStatusRequest request) {

        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("점검 모드 토글 요청 - 사용자: {}, 점검모드: {}", currentUserId, request.isMaintenanceMode());

        MaintenanceStatusResponse response = maintenanceService.toggleMaintenanceMode(
                request.isMaintenanceMode(), request.getMessage(), request.getEstimatedTime());
        return ResponseEntity.ok(response);
    }

    // 테스트용 간단한 토글 API
    @PostMapping("/toggle-simple")
    public ResponseEntity<MaintenanceStatusResponse> toggleMaintenanceModeSimple(
            @RequestParam(defaultValue = "true") boolean enable) {

        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("점검 모드 간단 토글 요청 - 사용자: {}, 활성화: {}", currentUserId, enable);

        MaintenanceStatusResponse response = maintenanceService.toggleMaintenanceMode(
                enable,
                enable ? "시스템 점검 중입니다. 잠시만 기다려주세요." : "",
                enable ? "30분 후" : "");
        return ResponseEntity.ok(response);
    }

    // 유지보수 설정 관리 API들
    @GetMapping("/settings")
    public ResponseEntity<MaintenanceSettingsResponse> getMaintenanceSettings() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("유지보수 설정 조회 요청 - 사용자: {}", currentUserId);

        MaintenanceSettingsResponse response = maintenanceService.getMaintenanceSettings();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/settings")
    public ResponseEntity<MaintenanceSettingsResponse> createMaintenanceSettings(
            @RequestBody MaintenanceSettingsRequest request) {

        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("유지보수 설정 생성 요청 - 사용자: {}, 점검모드: {}", currentUserId, request.getIsMaintenanceMode());

        MaintenanceSettingsResponse response = maintenanceService.createMaintenanceSettings(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/settings")
    public ResponseEntity<MaintenanceSettingsResponse> updateMaintenanceSettings(
            @RequestBody MaintenanceSettingsRequest request) {

        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("유지보수 설정 업데이트 요청 - 사용자: {}, 점검모드: {}", currentUserId, request.getIsMaintenanceMode());

        MaintenanceSettingsResponse response = maintenanceService.updateMaintenanceSettings(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/settings")
    public ResponseEntity<Void> deleteMaintenanceSettings() {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("유지보수 설정 삭제 요청 - 사용자: {}", currentUserId);

        maintenanceService.deleteMaintenanceSettings();
        return ResponseEntity.ok().build();
    }
}
