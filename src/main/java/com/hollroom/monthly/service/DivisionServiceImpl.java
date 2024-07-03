package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.entity.DivisionEntity;
import com.hollroom.monthly.repository.DivisionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {
    private final DivisionRepository divisionRepo;
    private final ModelMapper mapper;

    @Override
    public DivisionDTO readMainDivision(String addr) {
        String[] divisions= addr.split(" ");
        DivisionEntity main= divisionRepo.findByName(divisions[0]).get(0);
        for(int i=1;i<divisions.length;i++)
            main = divisionRepo.findByTopDivisionCodeAndName(main.mainDivisionCode,divisions[i]);

        return mapper.map(main, DivisionDTO.class);
    }

    @Override
    public List<DivisionDTO> readSubDivisions(String addr) {
        DivisionDTO main = readMainDivision(addr);
        return divisionRepo.findByTopDivisionCode(main.mainDivisionCode)
                .stream()
                .map(e->mapper.map(e,DivisionDTO.class))
                .collect(Collectors.toList());
    }
}
