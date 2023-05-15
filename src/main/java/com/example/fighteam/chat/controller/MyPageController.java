package com.example.fighteam.chat.controller;

import com.example.fighteam.chat.domain.dto.MyPageDto;
import com.example.fighteam.chat.domain.repository.ChatRoom;
import com.example.fighteam.chat.domain.repository.ChatRoomRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * @author : 김효준
 * @fileName : MyPageController
 * @since : 2023/05/12
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final ChatRoomRepository chatRoomRepository;
    @GetMapping("/mypageChat")
    public String mypageChatList(Model model, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("loginId");
        List<ChatRoom> findChatRoom = chatRoomRepository.findAllByUserIdOrPostUserId(userId,userId);

        List<MyPageDto> responseDto = new ArrayList<>();
        for (ChatRoom room : findChatRoom) {
            if(room.getMessages().size()==0)continue;
            responseDto.add(new MyPageDto(room.getMessages().get(room.getMessages().size()-1).getCreatedTime(),
                    room.getMessages().get(room.getMessages().size()-1).getMessage(), room.getId()));
            log.info("{} {} {}",responseDto.get(0).getCreatedTime(),responseDto.get(0).getMessage(),responseDto.get(0).getRoomId());
        }

        Collections.sort(responseDto, Comparator.comparing(MyPageDto::getCreatedTime));
        Collections.reverse(responseDto);
        model.addAttribute("chatList", responseDto);

        return "chat/chatList";
    }
}
