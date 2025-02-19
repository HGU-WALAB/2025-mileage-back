package com.csee.swplus.mileage.subitem.service;

import com.csee.swplus.mileage.subitem.dto.SubitemRequestDto;
import com.csee.swplus.mileage.subitem.dto.SubitemResponseDto;
import com.csee.swplus.mileage.subitem.mapper.SubitemMapper;
import com.csee.swplus.mileage.subitem.repository.SubitemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubitemService {
    private final SubitemMapper subitemMapper; // ✅ Mapper 사용!

    public List<SubitemResponseDto> getSubitems(SubitemRequestDto requestDto) {
        return subitemMapper.findSubitems(
                requestDto.getStudentId(),
                requestDto.getKeyword(),
                requestDto.getCategory(),
                requestDto.getSemester(),
                requestDto.getDone()
        );
    }
}
