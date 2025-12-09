package com.csee.swplus.mileage.maintenance.service;

import com.csee.swplus.mileage.maintenance.domain.Maintenance;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceSettingsRequest;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceSettingsResponse;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceStatusRequest;
import com.csee.swplus.mileage.maintenance.dto.MaintenanceStatusResponse;
import com.csee.swplus.mileage.maintenance.mapper.MaintenanceMapper;
import com.csee.swplus.mileage.maintenance.repository.MaintenanceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaintenanceService {
    
    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final ObjectMapper objectMapper;

    public MaintenanceStatusResponse getMaintenanceStatus(String email) {
        boolean isAllowedUser = false;
        boolean currentMaintenanceMode = false;
        String currentMessage = "";
        String currentEstimatedTime = "";
        
        // 데이터베이스에서 현재 유지보수 설정 조회
        Optional<Maintenance> maintenanceOpt = maintenanceRepository.findByIsActiveTrue();
        
        if (maintenanceOpt.isPresent()) {
            Maintenance maintenance = maintenanceOpt.get();
            currentMaintenanceMode = maintenance.getIsMaintenanceMode() != null ? maintenance.getIsMaintenanceMode() : false;
            currentMessage = maintenance.getMaintenanceMessage() != null ? maintenance.getMaintenanceMessage() : "";
            currentEstimatedTime = maintenance.getEstimatedTime() != null ? maintenance.getEstimatedTime() : "";
            
            // 이메일이 제공된 경우 허용된 사용자인지 확인
            if (email != null && !email.trim().isEmpty() && maintenance.getAllowedEmails() != null) {
                try {
                    List<String> allowedEmails = objectMapper.readValue(
                        maintenance.getAllowedEmails(), 
                        new TypeReference<List<String>>() {}
                    );
                    isAllowedUser = allowedEmails.contains(email.trim().toLowerCase());
                    log.info("이메일 {} 허용 여부: {}, 허용된 이메일 목록: {}", email, isAllowedUser, allowedEmails);
                } catch (Exception e) {
                    log.error("허용된 이메일 목록 파싱 실패", e);
                }
            } else {
                log.info("이메일이 제공되지 않거나 허용된 이메일 목록이 없음");
            }
        } else {
            log.info("유지보수 설정이 없음 - 점검 모드 비활성화");
        }
        
        log.info("점검 상태 확인 요청 - 이메일: {}, 현재 점검모드: {}", email, currentMaintenanceMode);
        
        // 점검 모드가 켜져있고 허용된 사용자가 아닌 경우에만 점검 페이지 표시
        boolean shouldShowMaintenance = currentMaintenanceMode && !isAllowedUser;
        
        log.info("최종 결과 - 점검 페이지 표시 여부: {}, 점검모드: {}, 허용사용자: {}", 
                shouldShowMaintenance, currentMaintenanceMode, isAllowedUser);
        
        return new MaintenanceStatusResponse(
                shouldShowMaintenance,
                currentMessage,
                currentEstimatedTime,
                isAllowedUser
        );
    }

    @Transactional
    public MaintenanceStatusResponse toggleMaintenanceMode(boolean maintenanceMode, String message, String estimatedTime) {
        // 기존 활성 설정을 비활성화
        Optional<Maintenance> existingMaintenance = maintenanceRepository.findByIsActiveTrue();
        if (existingMaintenance.isPresent()) {
            Maintenance maintenance = existingMaintenance.get();
            maintenance.setIsActive(false);
            maintenanceRepository.save(maintenance);
        }
        
        // 새로운 설정 생성
        Maintenance newMaintenance = Maintenance.builder()
                .isMaintenanceMode(maintenanceMode)
                .maintenanceMessage(message != null ? message : "")
                .estimatedTime(estimatedTime != null ? estimatedTime : "")
                .allowedEmails("[]") // 기본값으로 빈 배열
                .isActive(true)
                .build();
        
        maintenanceRepository.save(newMaintenance);
        
        log.info("점검 모드 변경: {} - 메시지: {} - 예상시간: {}", 
                maintenanceMode, message, estimatedTime);
        
        return new MaintenanceStatusResponse(
                maintenanceMode,
                message != null ? message : "",
                estimatedTime != null ? estimatedTime : "",
                false // 관리자용 응답이므로 isAllowedUser는 false
        );
    }
    
    @Transactional
    public MaintenanceSettingsResponse createMaintenanceSettings(MaintenanceSettingsRequest request) {
        // 기존 활성 설정을 비활성화
        Optional<Maintenance> existingMaintenance = maintenanceRepository.findByIsActiveTrue();
        if (existingMaintenance.isPresent()) {
            Maintenance maintenance = existingMaintenance.get();
            maintenance.setIsActive(false);
            maintenanceRepository.save(maintenance);
        }
        
        // 새로운 설정 생성
        Maintenance maintenance = maintenanceMapper.toEntity(request);
        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);
        
        log.info("새로운 유지보수 설정 생성: {}", savedMaintenance.getId());
        
        return maintenanceMapper.toResponse(savedMaintenance);
    }
    
    @Transactional
    public MaintenanceSettingsResponse updateMaintenanceSettings(MaintenanceSettingsRequest request) {
        Optional<Maintenance> existingMaintenance = maintenanceRepository.findByIsActiveTrue();
        
        if (existingMaintenance.isPresent()) {
            Maintenance maintenance = existingMaintenance.get();
            maintenanceMapper.updateEntity(maintenance, request);
            Maintenance savedMaintenance = maintenanceRepository.save(maintenance);
            
            log.info("유지보수 설정 업데이트: {}", savedMaintenance.getId());
            
            return maintenanceMapper.toResponse(savedMaintenance);
        } else {
            // 기존 설정이 없으면 새로 생성
            return createMaintenanceSettings(request);
        }
    }
    
    public MaintenanceSettingsResponse getMaintenanceSettings() {
        Optional<Maintenance> maintenance = maintenanceRepository.findByIsActiveTrue();
        
        if (maintenance.isPresent()) {
            return maintenanceMapper.toResponse(maintenance.get());
        } else {
            // 기본 설정 반환
            return MaintenanceSettingsResponse.builder()
                    .isMaintenanceMode(false)
                    .maintenanceMessage("")
                    .estimatedTime("")
                    .allowedEmails(java.util.Collections.emptyList())
                    .isActive(false)
                    .build();
        }
    }
    
    @Transactional
    public void deleteMaintenanceSettings() {
        Optional<Maintenance> maintenance = maintenanceRepository.findByIsActiveTrue();
        if (maintenance.isPresent()) {
            maintenance.get().setIsActive(false);
            maintenanceRepository.save(maintenance.get());
            log.info("유지보수 설정 비활성화: {}", maintenance.get().getId());
        }
    }
}
