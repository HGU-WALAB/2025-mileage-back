package com.csee.swplus.mileage.subitem.controller;

import com.csee.swplus.mileage.subitem.dto.SubitemRequestDto;
import com.csee.swplus.mileage.subitem.dto.SubitemResponseDto;
import com.csee.swplus.mileage.subitem.service.SubitemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mileage")
@RequiredArgsConstructor
public class SubitemController {
    private final SubitemService subitemService;

    @GetMapping("/{studentId}/search")
    public List<SubitemResponseDto> getSubitems(
            @PathVariable String studentId,
            @RequestParam(required = false) String query,
            @RequestParam String category,
            @RequestParam String semester,
            @RequestParam String done) {
        SubitemRequestDto requestDto = new SubitemRequestDto();
        requestDto.setStudentId(studentId);
        requestDto.setKeyword(query);
        requestDto.setCategory(category);
        requestDto.setSemester(semester);
        requestDto.setDone(done);
        return subitemService.getSubitems(requestDto);
    }
}