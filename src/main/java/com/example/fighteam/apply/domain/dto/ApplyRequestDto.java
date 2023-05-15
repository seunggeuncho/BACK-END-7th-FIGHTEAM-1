package com.example.fighteam.apply.domain.dto;

import lombok.Getter;

@Getter
public class ApplyRequestDto {

    private Long user_id;
    private Long post_id;

    public ApplyRequestDto(Long user_id, Long post_id) {
        this.user_id = user_id;
        this.post_id = post_id;
    }
}
