package com.csee.swplus.mileage.etcSubitem.controller;

import com.csee.swplus.mileage.etcSubitem.dto.DataWrapper;
import com.csee.swplus.mileage.etcSubitem.dto.EtcSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.service.EtcSubitemService;
import com.csee.swplus.mileage.util.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/etc")
@RequiredArgsConstructor
@Slf4j
public class EtcSubitemController {
    private final EtcSubitemService etcSubitemService;

    @GetMapping("")
    public ResponseEntity<DataWrapper> getStudentInputSubitems () {
        return ResponseEntity.ok(etcSubitemService.getStudentInputSubitems());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<DataWrapper> getRequestedStudentEtcSubitems (
            @PathVariable int studentId
    ) {
        return ResponseEntity.ok(etcSubitemService.getRequestedEtcSubitems(studentId));
    }

//    @PostMapping("/{studentId}")
//    public ResponseEntity<MessageResponseDto> postRequestedStudentEtcSubitems (
//            @PathVariable int studentId,
//            @ResponseBody
//    )
}
