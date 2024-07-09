package com.hollroom.admin.service;

import com.hollroom.admin.domain.dto.RecentInquiryDTO;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import com.hollroom.mypage.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RecentInquiryService {
    //================================================================================================================//
    private final InquiryRepository inquiryRepository;
    //================================================================================================================//
    public List<RecentInquiryDTO> getAllInquiries(){
        List<InquiryEntity> inquiryEntities = inquiryRepository.findAll();
        return inquiryEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    //================================================================================================================//
    private RecentInquiryDTO convertToDTO(InquiryEntity inquiryEntity){
        RecentInquiryDTO recentInquiryDTO = new RecentInquiryDTO();
        recentInquiryDTO.setId(inquiryEntity.getPostId());
        recentInquiryDTO.setTitle(inquiryEntity.getTitle());
        recentInquiryDTO.setTime(calculateTimeDifference(inquiryEntity.getCreatedAt()));
        recentInquiryDTO.setPriority(determinePriority(inquiryEntity));
        return recentInquiryDTO;
    }
    //================================================================================================================//
    private String calculateTimeDifference(LocalDateTime registrationTime){
        Duration duration = Duration.between(registrationTime, LocalDateTime.now());
        if(duration.toMinutes() < 60){
            return duration.toMinutes() + " min";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " hrs";
        } else {
            return duration.toDays() + " days";
        }
    }
    //================================================================================================================//
    private String determinePriority(InquiryEntity inquiryEntity){
        long hours = Duration.between(inquiryEntity.getCreatedAt(), LocalDateTime.now()).toHours();
        if(hours < 1){
            return "text-success";
        } else if(hours < 24){
            return "text-danger";
        } else if(hours < 168){
            return "text-primary";
        } else {
            return "text-muted";
        }
    }
}
