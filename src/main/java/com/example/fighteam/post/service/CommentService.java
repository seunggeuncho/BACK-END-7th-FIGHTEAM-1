package com.example.fighteam.post.service;

import com.example.fighteam.post.domain.dto.CreateCommentDto;
import com.example.fighteam.post.domain.dto.GetCommentDto;
import com.example.fighteam.post.domain.repository.CommentRepository;

import java.util.List;

public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Long createComment(CreateCommentDto createCommentDto) {
        Long post_id;
        post_id = commentRepository.saveComment(createCommentDto);
        return post_id;
    }

    public List<GetCommentDto> getComment (Long post_id){
        List<GetCommentDto> getCommentDto;
        getCommentDto = commentRepository.findbyPostid(post_id);
        return getCommentDto;
    }

    public Boolean removeComment(Long commentId) {
        Boolean rs;
        rs = commentRepository.removeComment(commentId);
        return rs;
    }

    public Boolean updateComment(Long commentId, String comment) {
        Boolean rs;
        rs = commentRepository.updateComment(commentId, comment);
        return rs;
    }
}
