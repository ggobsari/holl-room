package com.hollroom.admin.controller;


import com.hollroom.admin.domain.dto.*;
import com.hollroom.admin.service.*;
import com.hollroom.user.entity.UserEntity;
import com.hollroom.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminMainController {
    @Autowired
    private InquiryListService inquiryListService;
    @Autowired
    private AdminComService adminCommunityService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminRoomService adminRoomService;
    @Autowired
    private AdminMonService adminMonService;

    private final AdminAttachService adminAttachService;

    private final UserRepository userRepository;

    private final HttpSession session;

    //메인페이지
    @GetMapping("/index")
    public String index() {
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        if(user != null && user.getUserAdmin()){
            return "/admin/admin_dashboard";
        } else{
            return "/monthly/monthly";
        }//interest 반환
    }

    @GetMapping("/getAuthenticatedUser")
    @ResponseBody
    public UserEntity getAuthenticatedUser(){
        return (UserEntity) session.getAttribute("USER_NICKNAME");
    }

    //문의하기 목록
    @GetMapping("/admin_inquiry")
    public String tablesData(Model model) {
        List<InquiryListDTO> boardlist = inquiryListService.selectInquiry();
        model.addAttribute("boardlist", boardlist);
        Long totalCount = inquiryListService.selectInquiryCount();
        Long answerCount = inquiryListService.selectInquiryAnswerCount();
        System.out.println(totalCount + " " + answerCount);
        model.addAttribute("totalCount", totalCount); //글 전체개수
        model.addAttribute("answerCount", answerCount); //답변개수
        return "admin/admin_inquiry";
    }

    //문의하기 상세 글
    @GetMapping("/admin_inquiry_detail")
    public String pagescontact(Model model, Long postId) {
        InquiryListDTO inquiryListDTO = inquiryListService.selectInquiryByPostId(postId); //글 상세정보
        model.addAttribute("inquiry", inquiryListDTO);
        List<AdminAttachDTO> filelist = adminAttachService.getAdminAttach(postId); //첨부파일
        model.addAttribute("filelist", filelist);

        return "admin/admin_inquiry_detail"; //interest 반환
    }

    InquiryListDTO inquiry = new InquiryListDTO();

    //문의하기답변 등록
    @PostMapping("/inquiry_answer")
    public String submitAnswer(@RequestParam("postId") Long postId,
                               @RequestParam("answer_content") String answerContent) {
        InquiryListDTO inquiry = new InquiryListDTO();
        inquiry.setPost_Id(postId);
        inquiry.setAnswer_Content(answerContent);
        inquiry.setAnswer_At(new Date());
        inquiryListService.updateInquiryAnswer(inquiry);
        return "redirect:tables-data";
    }

    //==============================================================================================
    //커뮤니티 글 목록
    @GetMapping("/admin_community")
    public String community(Model model) {
        List<AdminComDTO> boardlist = adminCommunityService.selectAdminCommunity();
        model.addAttribute("boardlist", boardlist);

        model.addAttribute("totalCount", adminCommunityService.selectAdminCommunityCount()); //글 전체개수
        model.addAttribute("freeCount", adminCommunityService.selectAdminCommunityFreeCount()); //자유 글 개수
        model.addAttribute("questionCount", adminCommunityService.selectAdminCommunityQuestionCount()); //질문 글 개수
        model.addAttribute("tipCount", adminCommunityService.selectAdminCommunityTipCount()); //꿀팁 글 개수
        model.addAttribute("recipeCount", adminCommunityService.selectAdminCommunityRecipeCount()); //레시피 글 개수

        return "admin/admin_community";
    }

    //커뮤니티 글 삭제
    @PostMapping("/community_deleted")
    public ResponseEntity<String> adminCommunityDeleted(@RequestParam("postId") Long postId) {
        try {
            AdminComDTO community = new AdminComDTO();
            community.setPost_Id(postId);
            community.setDeleted("true");
            community.setDeleted_At(new Date());
            adminCommunityService.deleteAdminCommunity(community);

            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("게시글 삭제 중 오류가 발생했습니다.");
        }
    }

    //커뮤니티 글 삭제취소
    @PostMapping("/community_delete_cancel")
    public ResponseEntity<String> adminCommunityDeleteCancel(@RequestParam("postId") Long postId) {
        try {
            AdminComDTO community = new AdminComDTO();
            community.setPost_Id(postId);
            community.setDeleted(null);
            community.setDeleted_At(null);
            adminCommunityService.deleteAdminCommunity(community);

            return ResponseEntity.ok("게시글 삭제가 취소되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("게시글 삭제 취소도중 오류가 발생했습니다.");
        }
    }

    //================================================================================================
    //유저 목록
    @GetMapping("/admin_user")
    public String adminUser(Model model) {
        List<AdminUserDTO> adminUser = adminUserService.selectAllUsers();
        model.addAttribute("boardlist", adminUser);

        model.addAttribute("totalCount", adminUser.size()); //전체 유저 수
        model.addAttribute("kakao", adminUserService.selectKakaoUsersCount()); //카카오 유저 수
        model.addAttribute("naver", adminUserService.selectNaverUsersCount()); //네이버 유저 수
        model.addAttribute("google", adminUserService.selectGoogleUsersCount()); //구글 유저 수

        return "admin/admin_user";
    }

    //유저 상세보기
    @GetMapping("/admin_userdetail")
    public String adminUserdetail(Long Id, Model model) {
        AdminUserDTO adminUserDTO = adminUserService.selectUserById(Id);
        model.addAttribute("user", adminUserDTO);
        return "admin/admin_userdetail";
    }

    //유저 밴
    @PostMapping("/id_banned")
    public ResponseEntity<String> adminUserBanned(@RequestParam("Id") Long Id) {
        try {
            AdminUserDTO user = new AdminUserDTO();
            user.setId(Id);
            user.setBan(Boolean.TRUE);
            user.setIs_Deleted_At(new Date());

            adminUserService.selectUserBan(user);

            return ResponseEntity.ok("유저를 벤하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("유저 벤 도중 오류가 발생했습니다.");
        }
    }

    //유저 밴취소
    @PostMapping("/id_bancancel")
    public ResponseEntity<String> adminUserBanCancel(@RequestParam("Id") Long Id) {
        try {
            AdminUserDTO user = new AdminUserDTO();
            user.setId(Id);
            user.setBan(Boolean.FALSE);
            user.setIs_Deleted_At(null);

            adminUserService.selectUserBan(user);

            return ResponseEntity.ok("유저 벤 취소하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("유저 벤 취소 도중 오류가 발생했습니다.");
        }
    }

    //=====================================================================================
    //룸메이트 글 목록
    @GetMapping("/admin_roommate")
    public String adminRoommate(Model model) {
        List<AdminRoomDTO> adminRoomDTO = adminRoomService.selectAllAdminRoommates();
        model.addAttribute("boardlist", adminRoomDTO);
        return "admin/admin_roommate";
    }

    //룸메이트 글 삭제
    @PostMapping("/room_deleted")
    public ResponseEntity<String> adminRoomDeleted(@RequestParam("post_Id") Long post_Id) {
        try {
            AdminRoomDTO roommate = new AdminRoomDTO();
            roommate.setRoommate_Id(post_Id);
            roommate.setDeleted("Y");
            roommate.setDeleted_At(new Date());

            adminRoomService.selectAdminRoommateById(roommate);

            return ResponseEntity.ok("룸메이트 글을 삭제하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("룸메이트 글 삭제 도중 오류가 발생했습니다.");
        }
    }

    //룸메이트 글 삭제취소
    @PostMapping("/room_deleted_cancel")
    public ResponseEntity<String> adminRoomDeleteCancel(@RequestParam("post_Id") Long post_Id) {
        try {
            AdminRoomDTO roommate = new AdminRoomDTO();
            roommate.setRoommate_Id(post_Id);
            roommate.setDeleted("N");
            roommate.setDeleted_At(null);

            adminRoomService.selectAdminRoommateById(roommate);

            return ResponseEntity.ok("룸메이트 글 삭제를 취소하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("룸메이트 글 삭제 취소 도중 오류가 발생했습니다.");
        }
    }

    //=====================================================================================
    //월세매물 글 목록
    @GetMapping("/admin_monthly")
    public String monthly(Model model) {
        List<AdminMonDTO> adminMonDTOList = adminMonService.selectAllAdminMon();
        model.addAttribute("boardlist", adminMonDTOList);
        return "admin/admin_monthly"; //interest 반환
    }

    //월세매물 글 삭제
    @PostMapping("/mon_deleted")
    public ResponseEntity<String> adminMonDeleted(@RequestParam("post_Id") Long post_Id) {
        try {
            AdminMonDTO monthly = new AdminMonDTO();
            monthly.setId(post_Id);
            monthly.setDeleted_At(new Date());

            adminMonService.selectAdminMonById(monthly);

            return ResponseEntity.ok("월세매물 글을 삭제하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("월세매물 글 삭제 도중 오류가 발생했습니다.");
        }
    }

    //월세매물 글 삭제취소
    @PostMapping("/mon_deleted_cancel")
    public ResponseEntity<String> adminMonDeleteCancel(@RequestParam("post_Id") Long post_Id) {
        try {
            AdminMonDTO monthly = new AdminMonDTO();
            monthly.setId(post_Id);
            monthly.setDeleted_At(null);

            adminMonService.selectAdminMonById(monthly);

            return ResponseEntity.ok("월세매물 글 삭제를 취소하였습니다.");
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그를 출력합니다.
            return ResponseEntity.status(500).body("월세매물 글 삭제 취소 도중 오류가 발생했습니다.");
        }
    }

    //====================================================================================
    //레이아웃 컨트롤러
    //메인페이지
//    @GetMapping("/admin_layout")
//    public String admin_layout() {
//        return "admin/admin_layout"; //interest 반환
//    }
}
