package com.example.fighteam.post.domain.dto;

import lombok.Getter;

@Getter
public class DeleteProjectRequestDto {

    private String post_id;
    private Long user_id;

    public DeleteProjectRequestDto(String post_id, Long user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
    }
}
