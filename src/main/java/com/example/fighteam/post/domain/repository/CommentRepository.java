package com.example.fighteam.post.domain.repository;

import com.example.fighteam.post.domain.dto.CreateCommentDto;
import com.example.fighteam.post.domain.dto.GetCommentDto;

import java.util.List;

public interface CommentRepository {
    Long saveComment(CreateCommentDto createCommentDto);

    List<GetCommentDto> findbyPostid(Long Post_id);

    Boolean removeComment(Long commentId);

    Boolean updateComment(Long commentId, String comment);
}
