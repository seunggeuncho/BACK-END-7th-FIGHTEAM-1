package com.example.fighteam.post.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class GetPostDetailResponseDto {
    private Long post_id;
    private int count;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startdate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date enddate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    private int recruitdate;      //개발 기간
    private int deposit;
    private String typeContent;   //모집 포지션
    private String languageContent;   //기술 스택
    private String title;
    private String content;
    private Long user_id;
    private String subject;
    private String complete;
}
