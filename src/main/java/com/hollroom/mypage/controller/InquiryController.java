package com.hollroom.mypage.controller;


import com.hollroom.community.service.FileUploadService;
import com.hollroom.mypage.domain.dto.InquiryAttatchDTO;
import com.hollroom.mypage.domain.dto.InquiryDTO;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import com.hollroom.mypage.service.InquiryService;
import com.hollroom.mypage.service.ProfileService;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final ProfileService profileService;

    // 세션 체크 헬퍼 메서드
    private boolean checkSession(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        return user != null;
    }

    //문의하기 글목록
    @GetMapping("/inquiry")
    public String getInquiryList(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page, 20); // 한 페이지에 10개씩 표시
        Page<InquiryDTO> inquiryPage = inquiryService.getInquiries(pageable);

        model.addAttribute("inquiryPage", inquiryPage);
        return "mypage/inquiry";
    }

    //문의하기 글쓰기버튼 컨트롤러
    @GetMapping("/inquiry_write")
    public String inquiry_write(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        if (!checkSession(session)) {
            return "redirect:/login";
        }

        model.addAttribute("profile", profileService.getUserByEmail(user.getUserEmail()));
        return "mypage/inquiry_write"; //inquiry_write 반환
    }

    //문의글 업로드
    @PostMapping("/upload")
    public ResponseEntity<?> uploadInquiry(HttpSession session,
                                           @RequestParam("category") String category,
                                           @RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        try {
            // 세션에서 userID 가져오기
            UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":\"로그인이 필요합니다.\"}");
            }
            // 문의 정보 생성
            InquiryDTO inquiryDTO = new InquiryDTO();
            inquiryDTO.setCategory(category);
            inquiryDTO.setTitle(title);
            inquiryDTO.setContent(content);
            inquiryDTO.setUserId(user.getId());  // userId 설정

            InquiryEntity inquiryEntity = inquiryService.saveInquiry(inquiryDTO); //문의 글 저장
            if(files != null && !files.isEmpty()) {
                inquiryService.saveInquiryFile(files, inquiryEntity.getPostId()); //파일 첨부
            }

            // 성공 응답
            return ResponseEntity.ok().body("{\"message\":\"업로드 성공\"}");
        } catch (Exception e) {
            e.printStackTrace();
            // 실패 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"업로드 실패: " + e.getMessage() + "\"}");
        }
    }

    //문의글 상세보기
    @GetMapping("/inquiry/{id}")
    public String getInquiryDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!checkSession(session)) {
            return "redirect:/login";
        }

        InquiryDTO inquiryDTO = inquiryService.getInquiryById(id); //글 불러오기
        List<InquiryAttatchDTO> files = inquiryService.getAttachmentsByPostId(id);//첨부파일 불러오기

        model.addAttribute("inquiry", inquiryDTO);
        inquiryDTO.setFiles(files);
        return "mypage/inquiry_detail";
    }
}
