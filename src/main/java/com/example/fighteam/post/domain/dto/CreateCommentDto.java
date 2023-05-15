package com.example.fighteam.post.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CreateCommentDto {
    private Long user_id;
    private String comment;
    private Long post_id;
}
