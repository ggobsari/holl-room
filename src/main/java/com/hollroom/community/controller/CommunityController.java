package com.hollroom.community.controller;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.dto.CommunityFileDTO;
import com.hollroom.community.domain.dto.CommunityPagingDTO;
import com.hollroom.community.domain.dto.CommunityRequestDTO;
import com.hollroom.community.domain.dto.CommunityResponseDTO;
import com.hollroom.community.service.CommunityService;
import com.hollroom.community.service.FileUploadService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService service;
    private final FileUploadService fileUploadService;
    //일단 현우님 로그인 부분을 "user"라고 생각하고 만들어보자

    @GetMapping("/write")
    public String writePage(){
        return "community/content/community_write";
    }

    @PostMapping("/write")
    public String write(CommunityRequestDTO requestDTO, HttpSession session) throws IOException {
        //디버깅용
        System.out.println(requestDTO);

        //CommunityRequestDTO에서 files 정보만 가져오기
        List<MultipartFile> file = requestDTO.getFiles();
        List<CommunityFileDTO> files = fileUploadService.uploadFiles(file);
        System.out.println("커뮤니티파일매핑::"+files);

        service.insert(requestDTO, files);
        return "redirect:/community/list?category=all&page=0"; //
    }

    @GetMapping("/list")
    public ModelAndView listPage(@RequestParam("category") String category, @RequestParam("page") String page){
        ModelAndView mav = new ModelAndView("community/content/community_list");
        CommunityPagingDTO pagingDTO = service.communityList(category, page);
        System.out.println("list값 확인::"+ pagingDTO);

        mav.addObject("communityList", pagingDTO);
        mav.addObject("category", category);

        return mav;
    }
//    @PostMapping("/search")
//    public ModelAndView search(@RequestParam("category") String category, @RequestParam("search") String search){
//        //서치 한 뒤엔, 무조건 첫번째 페이지를 보여줄?
//        ModelAndView mav = new ModelAndView("community/content/community_list");
//
//
//        return mav;
//    }


    @GetMapping("/read") //조회수 업데이트 추가하기
    public String read(@RequestParam("postId") String postId,@RequestParam("action") String action, Model model){
        System.out.println("readparam확인::"+postId+", "+action);
        List<CommunityFileDTO> fileList = service.fileList(postId);
        CommunityResponseDTO readinfo = service.read(postId);
        System.out.println(fileList+","+readinfo);

        String view = "";
        if (action.equals("READ")) {
            view = "community/content/community_read";
            //read일 때는 해당 게시글 조회수 +1 증가시키기
            service.updateViewCounting(readinfo.getPostId());

        }else{ //UPDATE일 때
            view = "community/content/community_update";
        }
        // 공유 데이터
        model.addAttribute("readinfo",readinfo);
        model.addAttribute("category",readinfo.getCategory());
        //첨부파일 데이터 공유
        model.addAttribute("fileList",fileList);


        return view;
    }

    //게시글 수정
    @PostMapping("/update")
    public String update(CommunityRequestDTO requestDTO){
        System.out.println("업데이트 할 것들=>"+requestDTO);
        //반영할 수정목록 - 제목, 카테고리, 본문내용
        service.update(requestDTO);
        return "redirect:/community/list?category=all&page=0";
    }

}
