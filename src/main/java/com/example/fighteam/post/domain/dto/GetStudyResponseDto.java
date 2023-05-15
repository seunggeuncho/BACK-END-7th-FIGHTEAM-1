package com.example.fighteam.post.domain.dto;

import com.example.fighteam.post.domain.repository.CommentJdbc;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class GetStudyResponseDto {
    private int memberCount;
    private Date startDate;
    private Date endDate;
    private int deposit;
    private int recruitmentperiod;
    private List<String> languageContent;
    private List<String> typeContent;
    private String title;
    private String content;
    private List<CommentJdbc> comment;
    private Long user_id;

    public GetStudyResponseDto(int memberCount, Date startDate, Date endDate, int deposit, int recruitmentperiod, List<String> languageContent, List<String> typeContent, String title, String content, List<CommentJdbc> comment, Long userId) {
        this.memberCount = memberCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.recruitmentperiod = recruitmentperiod;
        this.languageContent = languageContent;
        this.typeContent = typeContent;
        this.title = title;
        this.content = content;
        this.comment = comment;
        user_id = userId;
    }
}
