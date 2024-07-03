package com.hollroom.monthly.service;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.monthly.dao.MonthlyProductDAO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class MonthlyProductServiceTest {
    @Autowired
    private MonthlyProductDAO dao;
    @Autowired
    private AttachFileRepository attachFileRepo;
    @Autowired
    private ModelMapper mapper;

    public void csvInsert() throws IOException {
        List<List<String>> attachFiles = readCSV("attach_file.csv");
        TabType[] types = TabType.values();
        attachFiles.forEach(f->{
            AttachFileEntity entity = new AttachFileEntity(
                    null,
                    types[Integer.parseInt(f.get(3))],
                    f.get(0),
                    f.get(1),
                    Long.parseLong(f.get(2))
            );
            attachFileRepo.save(entity);
        });
    }

    public List<List<String>> readCSV(String csvFile) throws IOException {
        String path = System.getProperty("user.dir") +"\\src\\test\\java\\com\\hollroom\\monthly\\service\\";
        List<List<String>> list = new ArrayList<>();
        File csv = new File(path+csvFile);
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csv))){
            br.readLine();
            while((line=br.readLine())!=null){
                String[] split = line.split(",");
                List<String> row = Arrays.asList(split);
                list.add(row);
            }
        };
        return list;
    }
}