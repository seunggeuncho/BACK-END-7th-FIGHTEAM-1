package com.example.fighteam.post.domain.dto;

import lombok.Getter;

@Getter
public class SearchStudyRequestDto {
    private String language;

    public SearchStudyRequestDto(String language) {
        this.language = language;
    }
}
