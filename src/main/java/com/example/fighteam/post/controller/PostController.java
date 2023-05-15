package com.example.fighteam.post.controller;

import com.example.fighteam.apply.domain.dto.ApplyResponseDto;
import com.example.fighteam.apply.service.ApplyService;
import com.example.fighteam.post.domain.dto.*;
import com.example.fighteam.post.service.CommentService;
import com.example.fighteam.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    public PostController(PostService postService, CommentService commentService, ApplyService applyService) {
        this.postService = postService;
        this.commentService = commentService;
        this.applyService = applyService;
    }


    PostService postService;

    ApplyService applyService;

    CommentService commentService;

    @GetMapping("/createpost")  //게시판 생성
    public String createForm(HttpSession session) {
        Long user_id = (Long)session.getAttribute("loginId");
        System.out.println("user_id = " + user_id);
        if (user_id != null){
            return "post/createpost";
        }else{
            return "redirect:/user/login";
        }
    }

    @PostMapping("/createpost") //게시판 생성
    public String create(CreatePostDto createPostDto, Model model, HttpSession session){
        CreatePostDto createPostDto1;
        createPostDto1 = createPostDto;
        Long user_id = (Long)session.getAttribute("loginId");
        createPostDto1.setUser_id(user_id);
        createPostDto1.setComplete("0");
        model.addAttribute("post", createPostDto1);
        postService.createProject(createPostDto);

        return "redirect:/post/home";
    }



    @GetMapping("/home")
    public String home(@RequestParam(defaultValue="1") int currentPage, HttpSession session, Model model
            ,@RequestParam(defaultValue = "null") String language, @RequestParam(defaultValue = "null") String type){
        List<GetBoardResponseDto> getBoardResponseDto;
        int total = 0;

        if ((language.equals("null" ) || language.equals("total")) && (type.equals("total") || type.equals("null"))){//전체 검색

            getBoardResponseDto = postService.findAll("", currentPage);
            total = postService.CountBoardList("");
            if(language.equals("total")){
                model.addAttribute("language", language);
            }
            if(type.equals("total") ){
                model.addAttribute("type", type);
            }
        }else if(language.equals("null") || language.equals("total")){          //type만 있는 상태
            if(type.equals("study")){
                getBoardResponseDto = postService.findAll("study", currentPage);
                total = postService.CountBoardList("study");
            }else{
                getBoardResponseDto = postService.findAll("project", currentPage);
                total = postService.CountBoardList("project");
            }
            if(language.equals("total")){
                model.addAttribute("language", language);
            }
            model.addAttribute("type", type);
        }else if((type.equals("total") || type.equals("null"))){                //language만 있는 상태
            getBoardResponseDto = postService.findByLanguage("",currentPage, language);
            total = postService.CountBoardListByLanguage("",language);
            if(type.equals("total") ){
                model.addAttribute("type", type);
            }
            model.addAttribute("language", language);
        }else{                                                                  //type, language 둘다 있는 상태
            getBoardResponseDto = postService.findByLanguage(type,currentPage, language);
            total = postService.CountBoardListByLanguage(type,language);
            model.addAttribute("language", language);
            model.addAttribute("type", type);
        }
        model.addAttribute("boardList", getBoardResponseDto);
        model.addAttribute("list", new ArticlePage(total, currentPage, 10, 5, getBoardResponseDto));
        model.addAttribute("total", total);
        return "post/home";
    }

    @GetMapping("/res/{id}")
    public String test(@PathVariable Long id, Model model, HttpSession session) {
        GetPostDetailResponseDto getPostDetailResponseDto;
        List<GetCommentDto> getCommentDto;
        getPostDetailResponseDto = postService.findByPostId(id);
        getCommentDto = commentService.getComment(getPostDetailResponseDto.getPost_id());
        int commentnum = getCommentDto.toArray().length;
        List<ApplyResponseDto> apply_List = applyService.getApplyList(id);

        model.addAttribute("board", getPostDetailResponseDto);
        model.addAttribute("commentList", getCommentDto);
        model.addAttribute("commentnum", commentnum);
        model.addAttribute("applyList", apply_List);
        model.addAttribute("post_id", id);
        return "post/postDetail";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model,HttpSession session){
        Long user_id = (Long)session.getAttribute("loginId");
        if (user_id != null){
            GetPostUpdateResponseDto getPostUpdateResponseDto;
            getPostUpdateResponseDto = postService.detailPostForUpdate(id);
            model.addAttribute("board", getPostUpdateResponseDto);
            return "post/updatepost";
        }else{
            return "user/login";
        }

    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, CreatePostDto createPostDto, RedirectAttributes redirectAttributes){
        postService.updatepost(createPostDto,id);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/post/res/{id}";
    }


    @GetMapping("/myboardcheck")
    public String paging(Model model, HttpSession session){
        Long id = (Long) session.getAttribute("loginId");
        List<GetBoardResponseDto> getBoardResponseDto;

        getBoardResponseDto = postService.findPostbyUserId(id);

        model.addAttribute("boardList", getBoardResponseDto);
        return "post/myboardcheckForm";
    }


    @GetMapping("/test2")
    public String test2(@RequestParam(defaultValue="1") int currentPage, HttpSession session, Model model,@RequestParam(defaultValue = "null") String language){
        List<GetBoardResponseDto> getBoardResponseDto;
        int total = 0;
        /*Long id = (Long) session.getAttribute("user_id");
        System.out.println(id);*/
        if (language.equals("null" )){
            getBoardResponseDto = postService.findAll("", currentPage);
            total = postService.CountBoardList("");

        }else {
            getBoardResponseDto = postService.findByLanguage("",currentPage, language);
            total = postService.CountBoardListByLanguage("",language);
            model.addAttribute("language", language);
        }
        model.addAttribute("boardList", getBoardResponseDto);
        model.addAttribute("list", new ArticlePage(total, currentPage, 10, 5, getBoardResponseDto));
        model.addAttribute("total", total);
        return "post/test2";
    }

    @PostMapping("/remove")
    public ResponseEntity remove(Long post_id){
        boolean result;
        result = postService.removepost(post_id);
        if (result == true){
            return new ResponseEntity<>("complete", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("삭제를 실패하였습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/complete")
    public void complete(Long post_id){
        postService.completepost(post_id);
    }

    /*
    @GetMapping("/study")   //스터디 조회 게시판
    public String study(@RequestParam(defaultValue="1") int currentPage,HttpSession session,  Model model,@RequestParam(defaultValue = "null") String language){
        List<GetBoardResponseDto> getBoardResponseDto;
        int total = 0;
        if (language.equals("null") || language.equals("total")){
            getBoardResponseDto = postService.findAll("study", currentPage);
            total = postService.CountBoardList("study");

        }else {
            getBoardResponseDto = postService.findByLanguage("study",currentPage, language);
            total = postService.CountBoardListByLanguage("study",language);
            model.addAttribute("language", language);
        }
        model.addAttribute("boardList", getBoardResponseDto);
        model.addAttribute("list", new ArticlePage(total, currentPage, 10, 5, getBoardResponseDto));
        model.addAttribute("total", total);
        return "post/study";}

    @GetMapping("/project") //프로젝트 조회 게시판
    public String project(@RequestParam(defaultValue="1") int currentPage, Model model,@RequestParam(defaultValue = "null") String language){
        List<GetBoardResponseDto> getBoardResponseDto;
        int total = 0;
        if (language.equals("null") || language.equals("total")){
            getBoardResponseDto = postService.findAll("project", currentPage);
            total = postService.CountBoardList("project");

        }else {
            getBoardResponseDto = postService.findByLanguage("project",currentPage, language);
            total = postService.CountBoardListByLanguage("project",language);
            model.addAttribute("language", language);
        }
        model.addAttribute("boardList", getBoardResponseDto);
        model.addAttribute("list", new ArticlePage(total, currentPage, 10, 5, getBoardResponseDto));
        model.addAttribute("total", total);
        return "post/project";}*/
    /*
    @GetMapping("/paging")
    public String paging(@RequestParam(defaultValue="1") int currentPage, Model model){
        List<GetBoardResponseDto> getBoardResponseDto;
        getBoardResponseDto = postService.findAll("", currentPage);
        int total = postService.CountBoardList("");
        PageDto pageDto = new PageDto(currentPage, 10, total);
        model.addAttribute("boardList", getBoardResponseDto);
        model.addAttribute("list", new PageDto(currentPage, 10, total));
        model.addAttribute("total", total);
        return "post/paging";
    }*/
       /* @GetMapping("/home")    //전체 조회 게시판
    public String home(@RequestParam(defaultValue="1") int currentPage, HttpSession session, Model model,@RequestParam(defaultValue = "null") String language){
        List<GetBoardResponseDto> getBoardResponseDto;
        int total = 0;
        *//*Long id = (Long) session.getAttribute("user_id");
        System.out.println(id);*//*
        if (language.equals("null" ) || language.equals("total")){
            getBoardResponseDto = postService.findAll("", currentPage);
            total = postService.CountBoardList("");

        }else {
            getBoardResponseDto = postService.findByLanguage("",currentPage, language);
            total = postService.CountBoardListByLanguage("",language);
            model.addAttribute("language", language);
        }
        model.addAttribute("boardList", getBoardResponseDto);
        model.addAttribute("list", new ArticlePage(total, currentPage, 10, 5, getBoardResponseDto));
        model.addAttribute("total", total);
        return "post/home";}*/
}
