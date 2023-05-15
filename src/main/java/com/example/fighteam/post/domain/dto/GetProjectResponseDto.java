package com.example.fighteam.post.domain.dto;

import com.example.fighteam.post.domain.repository.CommentJdbc;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class GetProjectResponseDto {
    private int memberCount;
    private Date startDate;
    private Date endDate;
    private int deposit;
    private List<String> typeContent;
    private List<String> languageContent;
    private String title;
    private String content;
    private List<CommentJdbc> comment;

    public GetProjectResponseDto(int memberCount, Date startDate, Date endDate, int deposit, List<String> typeContent, List<String> languageContent, String title, String content, List<CommentJdbc> comment) {
        this.memberCount = memberCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.typeContent = typeContent;
        this.languageContent = languageContent;
        this.title = title;
        this.content = content;
        this.comment = comment;
    }
}
