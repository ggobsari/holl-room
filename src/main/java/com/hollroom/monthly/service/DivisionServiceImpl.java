package com.hollroom.monthly.service;

import com.hollroom.monthly.domain.dto.DivisionDTO;
import com.hollroom.monthly.domain.entity.DivisionEntity;
import com.hollroom.monthly.repository.DivisionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {
    private final static Long KOREA_DIVISION_CODE = 1000000000L;
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
    public String getAddress(Long divisionCode) {
        DivisionEntity entity = divisionRepo.findByMainDivisionCode(divisionCode);
        StringBuilder result = new StringBuilder(entity.name);
        while(!entity.getTopDivisionCode().equals(KOREA_DIVISION_CODE)){
            entity = divisionRepo.findByMainDivisionCode(entity.getTopDivisionCode());
            result.insert(0, entity.name + " ");
        }
        return result.toString();
    }

    @Override
    public List<DivisionDTO> readSubDivisions(String addr) {
        DivisionDTO main = readMainDivision(addr);
        return divisionRepo.findByTopDivisionCode(main.mainDivisionCode)
                .stream()
                .map(e->mapper.map(e,DivisionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> readSubDivisionCodes(Long divisionCode) {
        return getSubDivision(divisionCode)
                .stream()
                .map(DivisionEntity::getMainDivisionCode)
                .collect(Collectors.toList());
    }

    private List<DivisionEntity> getSubDivision(Long divisionCode) {
        List<DivisionEntity> subDivisions = divisionRepo.findByTopDivisionCode(divisionCode);
        List<DivisionEntity> deepDivisions = new ArrayList<>();
        subDivisions.forEach(d->deepDivisions.addAll(getSubDivision(d.getMainDivisionCode())));
        subDivisions.addAll(deepDivisions);
        return subDivisions;
    }
}
