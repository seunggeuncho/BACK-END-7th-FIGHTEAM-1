package com.example.fighteam.post.domain.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class CreatePostDto {
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
    private List<String> typeContent;   //모집 포지션
    private List<String> languageContent;   //기술 스택
    private String title;
    private String content;
    private Long user_id;
    private String subject;
    private String complete; //스터디 or 프로젝트


    public CreatePostDto(){}
    @Builder
    public CreatePostDto(Long post_id, int count, Date startdate, Date enddate, Date date, int recruitdate, int deposit, List<String> typeContent, List<String> languageContent, String title, String content, Long user_id, String subject, String complete) {
        this.post_id = post_id;
        this.count = count;
        this.startdate = startdate;
        this.enddate = enddate;
        this.date = date;
        this.recruitdate = recruitdate;
        this.deposit = deposit;
        this.typeContent = typeContent;
        this.languageContent = languageContent;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.subject = subject;
        this.complete = complete;
    }
}
