package com.example.fighteam.chat.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 김효준
 * @fileName : MyPageDto
 * @since : 2023/05/12
 */
@Getter
@NoArgsConstructor
public class MyPageDto {
    private LocalDateTime createdTime;
    private String message;
    private Long roomId;

    public MyPageDto(LocalDateTime createdTime, String message, Long roomId) {
        this.createdTime = createdTime;
        this.message = message;
        this.roomId = roomId;
    }
}
