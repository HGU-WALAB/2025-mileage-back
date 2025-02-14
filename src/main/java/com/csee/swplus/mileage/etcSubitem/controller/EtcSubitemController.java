package com.csee.swplus.mileage.etcSubitem.controller;

import com.csee.swplus.mileage.etcSubitem.dto.EtcSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.service.EtcSubitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/etc")
@RequiredArgsConstructor
@Slf4j
public class EtcSubitemController {
    private final EtcSubitemService etcSubitemService;

//    @GetMapping("")
//    public ResponseEntity<?> getStudentInputSubitems () {
//        try {
//            List<EtcSubitemResponseDto> res = etcSubitemService.getStudentInputSubitems();
//        } catch () {
//
//        }
//    }
}
