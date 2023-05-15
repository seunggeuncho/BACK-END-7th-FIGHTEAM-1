package com.example.fighteam.apply.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyResponseDto {

    private int score;
    private Long user_id;
    private String apply_status;
    private String email;
    private String name;

    public ApplyResponseDto(String name, String email,int score, Long user_id, String apply_status) {
        this.name =name;
        this.email = email;
        this.score = score;
        this.user_id = user_id;
        this.apply_status = apply_status;
    }
}
