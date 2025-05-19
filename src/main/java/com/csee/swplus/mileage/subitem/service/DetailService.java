package com.csee.swplus.mileage.subitem.service;

import com.csee.swplus.mileage.subitem.dto.SubitemResponseDto;
import com.csee.swplus.mileage.subitem.mapper.DetailMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailService {
    private final DetailMapper detailMapper;

    public List<SubitemResponseDto> getAllDetailSubitems(String studentId) {
        log.info("Fetching all detail subitems");
        return detailMapper.findAllDetailSubitems(studentId);
    }
}