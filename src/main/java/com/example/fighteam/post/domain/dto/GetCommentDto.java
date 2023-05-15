package com.example.fighteam.post.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetCommentDto {
    private Long comment_id;
    private String user_name;
    private Long user_id;
    private String comment;
    private Long post_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
    private Date commentCreatedTime;
}
