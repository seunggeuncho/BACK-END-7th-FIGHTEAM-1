package com.example.fighteam.chat.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : 김효준
 * @fileName : ChatMessageRequestDto
 * @since : 2023/04/30
 */
@Setter
@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {
    private String message;
    private String sender;
    private String roomId;

    public ChatMessageRequestDto(String message, String sender, String roomId) {
        this.message = message;
        this.sender = sender;
        this.roomId = roomId;
    }
}
