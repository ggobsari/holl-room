package com.hollroom.community.controller;

import com.hollroom.common.TabType;
import com.hollroom.community.domain.dto.*;
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
//        System.out.println("list값 확인::"+ pagingDTO);
        //페이징 처리 시 필요한 값들
        System.out.println("=============================================================================");
        System.out.println("전체 페이지 수=>"+pagingDTO.getTotalPages());
        System.out.println("현재 페이지 번호=>"+pagingDTO.getNowPageNumber());
        System.out.println("한 페이지에 출력되는 데이터=>"+pagingDTO.getPageSize());
        System.out.println("다음 페이지 존재 여부=>"+pagingDTO.isHasNextPage());
        System.out.println("이전 페이지 존재 여부=>"+pagingDTO.isHasPreviousPage());
        System.out.println("첫번째 페이지 인가? =>"+pagingDTO.isFirstPage());
        System.out.println("마지막 페이지 인가? =>"+pagingDTO.isLastPage());
        System.out.println("=============================================================================");

        //시작페이지(startPage), 마지막페이지(endPage) 값 계산
        // 하단에 노출시킬 페이지 수는 3개
        int pageLimit = 3;
        int startPage = (((int) (Math.ceil((double) (pagingDTO.getNowPageNumber()+1) / pageLimit))) -1) * pageLimit+1;
        int endPage = ((startPage + pageLimit -1) < pagingDTO.getTotalPages() ? (startPage + pageLimit -1) : pagingDTO.getTotalPages());

        mav.addObject("startPage", startPage);
        mav.addObject("endPage", endPage);

        mav.addObject("communityList", pagingDTO);
        mav.addObject("category", category);

        return mav;
    }
    @PostMapping("/search")
    public ModelAndView search(@RequestParam("category") String category, @RequestParam("search") String search){
//        System.out.println("검색 매핑 확인::"+category+","+search);
        ModelAndView mav = new ModelAndView("community/content/community_search_list");

        List<CommunityResponseDTO> searchedDTOlist = service.search(category,search);
//        System.out.println("검색한 결과>>>>>>>>>>>"+searchedDTOlist);

        mav.addObject("communityList", searchedDTOlist);
        mav.addObject("category", category);

        return mav;
    }


    @GetMapping("/read") //조회수 업데이트 추가하기
    public String read(@RequestParam("postId") String postId,@RequestParam("action") String action, Model model){
        System.out.println("readparam확인::"+postId+", "+action);
        //첨부파일 조회 시 커뮤니티 탭 타입으로도 조회를 해야 함
        TabType type = TabType.COMMUNITY;
        List<CommunityFileDTO> fileList = service.fileList(postId,type);
        CommunityResponseDTO readinfo = service.read(postId);
//        System.out.println(fileList+","+readinfo);

        String view = "";
        List<CommentsResponseDTO> commentsList = null;
        if (action.equals("READ")) {
            view = "community/content/community_read";
            //read일 때, 해당 게시글 조회수 +1 증가시키기
            service.updateViewCounting(readinfo.getPostId());
            //read일 때, 해당 게시글의 댓글들 불러오기
            commentsList =  service.commentsList(postId);
//            System.out.println("+++++++++++++++++++++++++++++++++"+responseDTOS);

        }else{ //UPDATE일 때
            view = "community/content/community_update";
        }
        // 공유 데이터

        model.addAttribute("readinfo",readinfo);
        model.addAttribute("category",readinfo.getCategory());
        //첨부파일 데이터 공유
        model.addAttribute("fileList",fileList);
        //댓글들 데이터 공유
        model.addAttribute("comments",commentsList);


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

    @GetMapping("/delete")
    public String delete(@RequestParam("postId") String postId){ //게시글 삭제(비활성화)
//        System.out.println("매핑확인delete==>"+postId);
        service.delete(postId);

        //일단 임시로 view 경로 설정
        return "redirect:/community/list?category=all&page=0";
    }

    //댓글 등록
    @PostMapping("/comments/write")
    public String commentsWrite(CommentsRequestDTO commentsRequestDTO){
//        System.out.println("댓글작성 매핑 확인::"+ commentsRequestDTO); 매핑확인
        service.commentInsert(commentsRequestDTO);
       String strAddress = "redirect:/community/read?postId="+commentsRequestDTO.getPostId()
               +"&action=READ";
        return strAddress;
    }

    //댓글 수정 시 뷰 전환
    @GetMapping("/comments/update")
    public String commentUpdateView(@RequestParam("commentId") String commentId, @RequestParam("postId") String postId, Model model){
        //해당 게시글 조회
        TabType type = TabType.COMMUNITY;
        List<CommunityFileDTO> fileList = service.fileList(postId,type);
        CommunityResponseDTO readinfo = service.read(postId);

        model.addAttribute("readinfo",readinfo);
        model.addAttribute("category",readinfo.getCategory());
        model.addAttribute("fileList",fileList);
        //해당 게시글의 수정할 댓글 정보를 다음 뷰에 전달
        model.addAttribute("updatingCommentId",commentId);
        model.addAttribute("updatingPostId",postId);

        return "community/content/community_comment_update";
    }

    @PostMapping("/comments/update")
    public String commentUpdate(CommentsRequestDTO commentsRequestDTO){
//        System.out.println("수정 될 댓글 정보 맵핑 확인::"+ commentsRequestDTO); 확인 완료
        //postId로 read페이지 redirect시 이용, comments는 업데이트 할 내용, commentId는 해당 댓글 entity 불러오기
        service.commentUpdate(commentsRequestDTO);

        String strAddress = "redirect:/community/read?postId="+commentsRequestDTO.getPostId()
                +"&action=READ";
        return strAddress;
    }

    @GetMapping("/comments/delete")
    public String commentDelete(@RequestParam("commentId") String commentId, @RequestParam("postId") String postId){
        //postId는 redirect 주소 연결, commentId는 해당 댓글 entity찾기용
        service.commentDelete(commentId);

        String strAddress = "redirect:/community/read?postId="+postId
                +"&action=READ";
        return strAddress;
    }


}
