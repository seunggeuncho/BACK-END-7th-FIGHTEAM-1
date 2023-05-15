package com.example.fighteam.chat.service;

import com.example.fighteam.chat.domain.dto.ChatMessageRequestDto;
import com.example.fighteam.chat.domain.dto.ChatMessageResponseDto;
import com.example.fighteam.chat.domain.repository.ChatMessage;
import com.example.fighteam.chat.domain.repository.ChatMessageRepository;
import com.example.fighteam.chat.domain.repository.ChatRoom;
import com.example.fighteam.chat.domain.repository.ChatRoomRepository;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : 김효준
 * @fileName : ChatMessageService
 * @since : 2023/05/02
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessageResponseDto messageSave(ChatMessageRequestDto messageDto){
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(messageDto.getRoomId())).orElse(null);
        User sendUser = userRepository.findById(Long.parseLong(messageDto.getSender())).orElse(null);
        String userName = sendUser.getName();
        if (chatRoom != null && sendUser != null) {
            ChatMessage message = new ChatMessage(messageDto.getMessage(), sendUser, chatRoom);
            chatMessageRepository.save(message);
        }
        return new ChatMessageResponseDto(messageDto.getSender(), messageDto.getMessage(), messageDto.getRoomId(), userName);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> messageInfoReturn(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        List<ChatMessage> messages = chatRoom.getMessages();
        List<ChatMessageResponseDto> result = new ArrayList<>();
        for (ChatMessage c : messages) {
            result.add(new ChatMessageResponseDto(String.valueOf(c.getSender().getId()), c.getMessage(), String.valueOf(roomId),c.getSender().getName()));
        }
        Collections.reverse(result);
        return result;
    }
}
