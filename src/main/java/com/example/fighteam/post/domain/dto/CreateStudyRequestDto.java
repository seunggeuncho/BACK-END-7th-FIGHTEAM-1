package com.example.fighteam.post.domain.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class CreateStudyRequestDto {
    private int memberCount;
    private Date startDate;
    private Date endDate;
    private int deposit;
    private String languageContent;
    private String title;
    private String content;
    private Long user_id;

    public CreateStudyRequestDto(int memberCount, Date startDate, Date endDate, int deposit, String languageContent, String title, String content, Long user_id) {
        this.memberCount = memberCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.languageContent = languageContent;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
    }
}
