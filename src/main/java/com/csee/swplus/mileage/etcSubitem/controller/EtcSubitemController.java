package com.csee.swplus.mileage.etcSubitem.controller;

import com.csee.swplus.mileage.etcSubitem.dto.EtcSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.dto.StudentInputSubitemResponseDto;
import com.csee.swplus.mileage.etcSubitem.service.EtcSubitemService;
import com.csee.swplus.mileage.util.message.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;

@RestController // 이 class 가 REST API 관련 class 라는 것을 스프링에게 명시
@RequestMapping("/api/mileage/etc")
@RequiredArgsConstructor
@Slf4j
public class EtcSubitemController {
    private final EtcSubitemService etcSubitemService;

    @GetMapping("")
    public ResponseEntity<List<StudentInputSubitemResponseDto>> getStudentInputSubitems () {
        return ResponseEntity.ok(
                etcSubitemService.getStudentInputSubitems()
        );
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<EtcSubitemResponseDto>> getEtcSubitems (
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(
                etcSubitemService.getEtcSubitems(studentId)
        );
    }

//    @PostMapping(value = "/test/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> testFileUpload(
//            @PathVariable int studentId,
//            @RequestPart MultipartFile file
//    ) {
//        return ResponseEntity.ok(
//          "File received: " + file.getOriginalFilename()
//        );
//    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> postEtcSubitem (
            @PathVariable String studentId,
            @RequestParam("semester") String semester,
            @RequestParam(value = "description1", required = false) String description1,
            @RequestParam(value = "description2", required = false) String description2,
            @RequestParam("subitemId") int subitemId,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) {
        log.info("Content Type: {}", file.getContentType());
        log.info("File Name: {}", file.getOriginalFilename());
        log.info("File Size: {}", file.getSize());

        return ResponseEntity.ok(
                etcSubitemService.postEtcSubitem(studentId, semester, description1, description2, subitemId, file)
        );
    }

    @PatchMapping(value = "/{studentId}/{recordId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> patchEtcSubitem (
            @PathVariable String studentId,
            @PathVariable int recordId,
            @RequestParam(value = "description1", required = false) String description1,
            @RequestParam(value = "description2", required = false) String description2,
            @RequestParam("subitemId") int subitemId,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        log.info("Content Type: {}", file.getContentType());
        log.info("File Name: {}", file.getOriginalFilename());
        log.info("File Size: {}", file.getSize());

        return ResponseEntity.ok(
                etcSubitemService.patchEtcSubitem(studentId, recordId, description1, description2, subitemId, file)
        );
    }

    @DeleteMapping("/{studentId}/{recordId}")
    public ResponseEntity<MessageResponseDto> deleteEtcSubitem(
            @PathVariable String studentId,
            @PathVariable int recordId
    ) {
        return ResponseEntity.ok(
                etcSubitemService.deleteEtcSubitem(studentId, recordId)
        );
    }
}
