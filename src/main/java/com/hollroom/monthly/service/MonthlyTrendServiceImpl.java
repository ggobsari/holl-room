package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.dto.MonthlyTrendDTO;
import com.hollroom.monthly.repository.MonthlyTrendRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthlyTrendServiceImpl implements MonthlyTrendService {
    private final MonthlyTrendRepository repo;
    private final DivisionService divisionService;
    private final ModelMapper mapper;

    @Override
    public MonthlyTrendDTO readMonthlyTrend(Long divisionCode) {
        return mapper.map(repo.findByDateCodeAndDivisionCode(getCurrentDateCode(),divisionCode), MonthlyTrendDTO.class);
    }

    @Override
    public Map<Long,MonthlyTrendDTO> readMonthlyTrendsByAddress(String address) {
        Map<Long,MonthlyTrendDTO> result = new HashMap<>();
        try {
            List<DivisionDTO> divisions = divisionService.readSubDivisions(address);
            divisions.forEach(d -> result.put(d.mainDivisionCode,readMonthlyTrend(d.mainDivisionCode)));
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private int getCurrentDateCode(){
        // 현재 날짜 구하기
         LocalDate now = LocalDate.now();
        // 포맷 정의
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        // 포맷 적용
         String formatedNow = now.format(formatter);

         return Integer.parseInt(formatedNow)-1;
    }
}
