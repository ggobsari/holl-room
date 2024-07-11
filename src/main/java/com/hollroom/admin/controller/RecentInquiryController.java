package com.hollroom.admin.controller;

import com.hollroom.admin.domain.dto.RecentInquiryDTO;
import com.hollroom.admin.service.RecentInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecentInquiryController {

    private final RecentInquiryService recentInquiryService;

    @GetMapping("/inquiries")
    public List<RecentInquiryDTO> getInquiries(){
        return recentInquiryService.getAllInquiries();
    }
}
