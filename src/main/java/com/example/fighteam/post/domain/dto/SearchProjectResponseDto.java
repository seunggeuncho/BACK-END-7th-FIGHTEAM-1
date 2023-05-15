package com.example.fighteam.post.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchProjectResponseDto {
    private String postTitle;
    private List<String> typeContent;
    private List<String> languageContent;

    public SearchProjectResponseDto(String postTitle, List<String> typeContent, List<String> languageContent) {
        this.postTitle = postTitle;
        this.typeContent = typeContent;
        this.languageContent = languageContent;
    }
}
