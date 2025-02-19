package com.csee.swplus.mileage.milestone.controller;

import com.csee.swplus.mileage.milestone.service.MilestoneService;
import com.csee.swplus.mileage.util.DataWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/capability")
@RequiredArgsConstructor
@Slf4j
public class MilestoneController {
    public final MilestoneService milestoneService;

    @GetMapping("")
    public ResponseEntity<DataWrapper> getMilestoneCapabilities() {
        return ResponseEntity.ok(
                milestoneService.getMilestoneCapabilities()
        );
    }
}
