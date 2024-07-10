package com.hollroom.monthly.service;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.entity.AttachFileEntity;
import com.hollroom.community.repository.AttachFileRepository;
import com.hollroom.monthly.dao.MonthlyProductDAO;
import com.hollroom.monthly.domain.entity.DivisionEntity;
import com.hollroom.monthly.domain.entity.MonthlyProductEntity;
import com.hollroom.monthly.domain.entity.MonthlyTrendEntity;
import com.hollroom.monthly.repository.DivisionRepository;
import com.hollroom.monthly.repository.MonthlyProductRepository;
import com.hollroom.monthly.repository.MonthlyTrendRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.*;
import java.sql.Date;
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
    private MonthlyProductRepository monthlyProductRepo;
    @Autowired
    private MonthlyTrendRepository monthlyTrendRepo;
    @Autowired
    private DivisionRepository divisionRepo;
    @Autowired
    private ModelMapper mapper;

    // 실행시키기 전,
    // properties의
    // #spring.jpa.hibernate.ddl-auto
    // 를 잠깐 create로 바꿀 것
    @Test
    public void csvInsert() throws IOException {
        try{
            List<MonthlyProductEntity> products = new ArrayList<>();
            List<List<String>> monthlyProduct = readCSV("monthly_product.csv");
            monthlyProduct.forEach(m->{
                MonthlyProductEntity entity = new MonthlyProductEntity(
                        null,
                        Integer.parseInt(m.get(1)), //deposit
                        Integer.parseInt(m.get(4)), //monthly
                        Long.parseLong(m.get(2)), //divisionCode
                        Integer.parseInt(m.get(3)), //floorCount
                        Float.parseFloat(m.get(5)), //pyeongCount
                        Integer.parseInt(m.get(6)), //roomCount
                        Integer.parseInt(m.get(0)), //bayCount
                        m.get(7), // roomOption
                        m.get(8), // securityFacility
                        Date.valueOf(m.get(9)), // expirationDate
                        null,
                        null,
                        null
                );
                products.add(monthlyProductRepo.save(entity));
            });


            List<List<String>> attachFiles = readCSV("attach_file.csv");
            TabType[] types = TabType.values();

            for(int i=0;i<attachFiles.size();i++){
                List<String> f = attachFiles.get(i);
                AttachFileEntity entity = new AttachFileEntity(
                        null,
                        types[Integer.parseInt(f.get(3))],
                        f.get(0),
                        f.get(1),
                        products.get(i).getId()
                );
                attachFileRepo.save(entity);
            }

            List<List<String>> division = readCSV("division.csv");
            division.forEach(d->{
                DivisionEntity entity = new DivisionEntity(
                        null,
                        Long.parseLong(d.get(1)),
                        Long.parseLong(d.get(2)),
                        d.get(3)
                );
                divisionRepo.save(entity);
            });

            List<List<String>> trend = readCSV("monthly_trend.csv");
            trend.forEach(d->{
                MonthlyTrendEntity entity = new MonthlyTrendEntity(
                        null,
                        Long.parseLong(d.get(1)),
                        d.get(2),
                        Integer.parseInt(d.get(0))
                );
                monthlyTrendRepo.save(entity);
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        // 0 == bay
        // 1 == deposit
        // 2 == division
        // 3 == floor
        // 4 == monthly
        // 5 == pyeong
        // 6 == room_counnt
        // 7 == room_option
        // 8 == security
        // 9 == expriation
    }

    @Test
    public void productInsert() throws IOException, NumberFormatException {
        List<MonthlyProductEntity> products = new ArrayList<>();
        List<List<String>> monthlyProduct = readCSV("monthly_product.csv");
        monthlyProduct.forEach(m->{
            MonthlyProductEntity entity = new MonthlyProductEntity(
                    null,
                    Integer.parseInt(m.get(1)),
                    Integer.parseInt(m.get(4)),
                    Long.parseLong(m.get(2)),
                    Integer.parseInt(m.get(3)),
                    Float.parseFloat(m.get(5)),
                    Integer.parseInt(m.get(6)),
                    Integer.parseInt(m.get(0)),
                    m.get(7),
                    m.get(8),
                    Date.valueOf(m.get(9)),
                    null,
                    null,
                    null
            );
            products.add(monthlyProductRepo.save(entity));
        });

        List<List<String>> attachFiles = readCSV("attach_file.csv");
        TabType[] types = TabType.values();

        for(int i=0;i<attachFiles.size();i++){
            List<String> f = attachFiles.get(i);
            AttachFileEntity entity = new AttachFileEntity(
                    null,
                    types[Integer.parseInt(f.get(3))],
                    f.get(0),
                    f.get(1),
                    products.get(i).getId()
            );
            attachFileRepo.save(entity);
        }
    }

    public List<List<String>> readCSV(String csvFile) throws IOException {
        String path = System.getProperty("user.dir") +"\\src\\test\\java\\com\\hollroom\\monthly\\service\\data\\csv\\";
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