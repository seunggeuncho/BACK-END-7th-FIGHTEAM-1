package com.example.fighteam.post.domain.dto;

import lombok.Getter;

import java.util.Date;
import java.util.List;
@Getter
public class UpdateProjectRequestDto {

    private int memberCount;
    private Date startDate;
    private Date endDate;
    private int deposit;
    private List<String> typeContent;
    private List<String> languageContent;
    private String title;
    private String content;
    private String post_id;
    private Long user_id;

    public UpdateProjectRequestDto(int memberCount, Date startDate, Date endDate, int deposit, List<String> typeContent, List<String> languageContent, String title, String content, String post_id, Long user_id) {
        this.memberCount = memberCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.typeContent = typeContent;
        this.languageContent = languageContent;
        this.title = title;
        this.content = content;
        this.post_id = post_id;
        this.user_id = user_id;
    }


}
