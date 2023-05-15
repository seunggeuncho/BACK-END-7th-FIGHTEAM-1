package com.example.fighteam.chat.controller;

import com.example.fighteam.chat.domain.repository.ChatRoom;
import com.example.fighteam.chat.domain.repository.ChatRoomRepository;
import com.example.fighteam.chat.service.ChatRoomService;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author : 김효준
 * @fileName : ChatController
 * @since : 2023/05/02
 */
@RequiredArgsConstructor
@Slf4j
@Controller
public class ChatController {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatService;

    @PostMapping("/chat/creating")
    public String createChatRoom(@RequestParam("postUser") Long findUser, HttpSession session){
        Long loginUser = (Long) session.getAttribute("loginId");
        if(findUser.equals(loginUser)) return "redirect:/mypageChat";
        else {
            ChatRoom chatRoom = chatRoomRepository.findByUserIdAndPostUserId(loginUser, findUser).orElse(null);
            ChatRoom chatRoom1 = chatRoomRepository.findByUserIdAndPostUserId(findUser, loginUser).orElse(null);
            if(chatRoom!=null) return "redirect:/chat/room/enter/" + chatRoom.getId();
            if(chatRoom1!=null) return "redirect:/chat/room/enter/" + chatRoom1.getId();
            Long[] chatMember = {findUser, loginUser};
            Long chatRoomId = chatService.createChat(chatMember);
            return "redirect:/chat/room/enter/" + chatRoomId;
        }
    }

    @GetMapping("/chat/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable Long roomId){
        return chatService.roomInfoReturn(roomId);
    }

    @GetMapping("/chat/room/user/{userId}")
    @ResponseBody
    public User sendUser(@PathVariable Long userId){
        return userRepository.findById(userId).orElseThrow(null);
    }

    @GetMapping("/chat/room/enter/{roomId}")
    public String enterChatroom(@PathVariable Long roomId, Model model, HttpSession session) {
        try {
            Long loginUserId = (Long) session.getAttribute("loginId");
            model.addAttribute("nowSender", loginUserId);
            model.addAttribute("roomInfo", roomId);
            return "chat/chat";
        } catch (Exception e) {
            log.error("NOT FIND CHATROOM : {}",e);
            return "redirect:/post/home";
        }
    }
}
