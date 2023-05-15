package com.example.fighteam.post.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchProjectRequestDto {
    private String subject;
    private List<String> languageContent;

    public SearchProjectRequestDto(String subject, List<String> languageContent) {
        this.subject = subject;
        this.languageContent = languageContent;
    }
}
