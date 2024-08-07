package com.hollroom.mypage.controller;

import com.hollroom.common.TabType;
import com.hollroom.mypage.domain.dto.InquiryAttatchDTO;
import com.hollroom.mypage.domain.dto.InquiryDTO;
import com.hollroom.mypage.domain.entity.InquiryEntity;
import com.hollroom.mypage.service.InquiryFileService;
import com.hollroom.mypage.service.InquiryService;
import com.hollroom.mypage.service.ProfileService;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final ProfileService profileService;
    private final InquiryFileService fileUploadService;

    // 세션 체크 헬퍼 메서드
    private boolean checkSession(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        return user != null;
    }

    //문의하기 컨트롤러
    @GetMapping("/inquiry")
    public String inquiry(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        if (user.getUserNickname() == null) { //유저 닉네임이 없을 경우
            return "redirect:/mypage/profile";
        }
        return "mypage/inquiry"; //interest 반환
    }

    //문의하기 글목록 컨트롤러
    @GetMapping("/inquiryposts")
    public ResponseEntity<?> getMyPosts(@RequestParam(name = "page", defaultValue = "0") int page,
                                        HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        Long userId = user.getId();


        Map<String, Object> result = inquiryService.getInquiriesByUserId(userId, page);
        model.addAttribute("posts", result.get("posts")); //게시물 정보
        model.addAttribute("totalPages", result.get("totalPages")); //총 게시물 페이지수
        model.addAttribute("totalPosts", result.get("totalPosts")); //총 게시물 개수
        model.addAttribute("user", user); //유저정보

        return new ResponseEntity<>(result, HttpStatus.OK);
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
            if (files != null && !files.isEmpty()) {
                TabType tabType = TabType.MYPAGE;
                List<InquiryAttatchDTO> file = fileUploadService.uploadFiles(files); // 파일 첨부
                System.out.println(file);

                inquiryService.saveInquiryFile(file, inquiryEntity.getPostId()); //첨부 파일 저장

                //서비스로 파일 삽입
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
        TabType tabType = TabType.MYPAGE;
        List<InquiryAttatchDTO> files = inquiryService.getAttachmentsByPostId(id, tabType);//첨부파일 불러오기
        inquiryDTO.setFiles(files);

        model.addAttribute("inquiry", inquiryDTO);
        return "mypage/inquiry_detail";
    }
}
