package com.example.fighteam.post.domain.dto;

import lombok.Getter;

@Getter
public class SearchStudyResponseDto {
    private String studyTitle;
    private String languageContent;

    public SearchStudyResponseDto(String studyTitle, String languageContent) {
        this.studyTitle = studyTitle;
        this.languageContent = languageContent;
    }
}
