package com.example.fighteam.chat.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @author : 김효준
 * @fileName : ChatMessageResponseDto
 * @since : 2023/04/30
 */
@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String sender;
    private String message;
    private String roomId;
    private String senderName;

    public ChatMessageResponseDto(String sender, String message, String roomId, String senderName) {
        this.sender = sender;
        this.message = message;
        this.roomId = roomId;
        this.senderName = senderName;
    }
}
