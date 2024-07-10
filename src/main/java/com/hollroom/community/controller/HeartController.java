package com.hollroom.community.controller;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.dto.CommentsResponseDTO;
import com.hollroom.community.domain.dto.CommunityFileDTO;
import com.hollroom.community.domain.dto.CommunityResponseDTO;
import com.hollroom.community.domain.dto.HeartDTO;
import com.hollroom.community.service.CommunityService;
import com.hollroom.community.service.HeartService;
import com.hollroom.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/heart")
@RequiredArgsConstructor
public class HeartController {
    private final CommunityService service;
    private final HeartService heartService;

    // 좋아요 활성화
    @PostMapping("/update1")
    public String upHeart(@RequestParam("postId") String postId, @RequestParam("heartId") String heartId, Model model, HttpSession session){

        //좋아요 활성화 서비스
        heartService.upHeartCount(Long.parseLong(postId),Long.parseLong(heartId));

        TabType type = TabType.COMMUNITY;
        List<CommunityFileDTO> fileList = service.fileList(postId,type);
        CommunityResponseDTO readinfo = service.read(postId);


        // 좋아요 가져오기
        HeartDTO heartInfo = null;
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        if(user!=null){
            Long userId = user.getId();
            heartInfo = service.getHeart(Long.parseLong(postId),userId);
//            System.out.println("좋아요 정보 가져오기======================"+heartInfo);
            if (heartInfo!=null){
                model.addAttribute("heartInfo",heartInfo);
            }
        }


        String view = "";
        List<CommentsResponseDTO> commentsList = null;

        view = "community/content/community_read";
        //read일 때, 해당 게시글의 댓글들 불러오기
        commentsList =  service.commentsList(postId);

        // 공유 데이터
        model.addAttribute("readinfo",readinfo);
        model.addAttribute("category",readinfo.getCategory());
        //첨부파일 데이터 공유
        model.addAttribute("fileList",fileList);
        //댓글들 데이터 공유
        model.addAttribute("comments",commentsList);


        return view;
    }


    // 좋아요 비활성화
    @PostMapping("/update0")
    public String downHeart(@RequestParam("postId") String postId, @RequestParam("heartId") String heartId, Model model, HttpSession session){

        //좋아요 비활성화 서비스
        heartService.downHeartCount(Long.parseLong(postId),Long.parseLong(heartId));

        TabType type = TabType.COMMUNITY;
        List<CommunityFileDTO> fileList = service.fileList(postId,type);
        CommunityResponseDTO readinfo = service.read(postId);


        // 좋아요 가져오기
        HeartDTO heartInfo = null;
        UserEntity user = (UserEntity) session.getAttribute("USER_NICKNAME");
        if(user!=null){
            Long userId = user.getId();
            heartInfo = service.getHeart(Long.parseLong(postId),userId);
//            System.out.println("좋아요 정보 가져오기======================"+heartInfo);
            if (heartInfo!=null){
                model.addAttribute("heartInfo",heartInfo);
            }
        }


        String view = "";
        List<CommentsResponseDTO> commentsList = null;

        view = "community/content/community_read";
        //read일 때, 해당 게시글의 댓글들 불러오기
        commentsList =  service.commentsList(postId);

        // 공유 데이터
        model.addAttribute("readinfo",readinfo);
        model.addAttribute("category",readinfo.getCategory());
        //첨부파일 데이터 공유
        model.addAttribute("fileList",fileList);
        //댓글들 데이터 공유
        model.addAttribute("comments",commentsList);


        return view;
    }

}
