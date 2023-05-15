package com.example.fighteam.chat.controller;

import com.example.fighteam.chat.domain.dto.ChatMessageRequestDto;
import com.example.fighteam.chat.domain.dto.ChatMessageResponseDto;
import com.example.fighteam.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : 김효준
 * @fileName : MessageController
 * @since : 2023/05/02
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessageRequestDto message) {
        ChatMessageResponseDto chatMessageResponseDto = chatMessageService.messageSave(message);
        messagingTemplate.convertAndSend("/queue/chat/room/enter/"+message.getRoomId(),chatMessageResponseDto);
    }

    @GetMapping("/room/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageResponseDto> messageInfo(@PathVariable Long roomId){
        return chatMessageService.messageInfoReturn(roomId);
    }
}
