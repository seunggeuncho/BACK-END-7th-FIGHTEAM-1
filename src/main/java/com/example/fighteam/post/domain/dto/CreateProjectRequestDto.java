package com.example.fighteam.post.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateProjectRequestDto {
    private int memberCount;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private int recruitmentperiod;      //모집 기간
    private int deposit;
    private List<String> typeContent;   //모집 포지션
    private List<String> languageContent;   //기술 스택
    private String title;
    private String content;
    private Long user_id;
    private String subject; //스터디 or 프로젝트

    public CreateProjectRequestDto(int memberCount, Date startDate, Date endDate, int recruitmentperiod, int deposit, List<String> typeContent, List<String> languageContent, String title, String content, Long user_id, String subject) {
        this.memberCount = memberCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recruitmentperiod = recruitmentperiod;
        this.deposit = deposit;
        this.typeContent = typeContent;
        this.languageContent = languageContent;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.subject = subject;
    }
}
