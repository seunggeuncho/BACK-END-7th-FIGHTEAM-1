package com.example.fighteam.chat.service;

import com.example.fighteam.chat.domain.repository.ChatRoom;
import com.example.fighteam.chat.domain.repository.ChatRoomRepository;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long createChat(Long[] users) {
        List<User> chatMember = userRepository.findAllById(Arrays.asList(users));
        ChatRoom createdChatRoom = new ChatRoom(chatMember.get(0), chatMember.get(1));
        chatRoomRepository.save(createdChatRoom);
        return createdChatRoom.getId();
    }

    @Transactional(readOnly = true)
    public ChatRoom roomInfoReturn(Long roomId) {
        return chatRoomRepository.findById(roomId).orElse(null);
    }

}
