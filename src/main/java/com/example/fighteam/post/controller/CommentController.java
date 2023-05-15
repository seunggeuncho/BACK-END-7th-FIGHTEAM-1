package com.example.fighteam.post.controller;

import com.example.fighteam.post.domain.dto.CreateCommentDto;
import com.example.fighteam.post.domain.dto.GetCommentDto;
import com.example.fighteam.post.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/save")
    public ResponseEntity save(CreateCommentDto createCommentDto){
        Long post_id = commentService.createComment(createCommentDto);
        if (post_id != null)
        {
            List<GetCommentDto> getCommentDto;
            getCommentDto = commentService.getComment(post_id);
            return new ResponseEntity<>(getCommentDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 게시글은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity remove(Long comment_id, Long post_id){
        Boolean result;
        result = commentService.removeComment(comment_id);
        if (result){
            List<GetCommentDto> getCommentDto;
            getCommentDto = commentService.getComment(post_id);
            return new ResponseEntity<>(getCommentDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("삭제를 실패하였습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public ResponseEntity update(Long comment_id, Long post_id, String comment){
        Boolean result;
        result = commentService.updateComment(comment_id, comment);
        if (result){
            List<GetCommentDto> getCommentDto;
            getCommentDto = commentService.getComment(post_id);
            return new ResponseEntity<>(getCommentDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("수정을 실패하였습니다.", HttpStatus.NOT_FOUND);
        }

    }
}
