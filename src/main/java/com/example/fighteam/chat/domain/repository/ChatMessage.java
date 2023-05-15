package com.example.fighteam.chat.domain.repository;

import com.example.fighteam.user.domain.repository.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 김효준
 * @fileName : ChatMessage
 * @since : 2023/04/26
 */
@Getter
@NoArgsConstructor
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column
    private String message;

    @Column
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHATROOM_ID", referencedColumnName = "id")
    private ChatRoom chatRoom;

    public ChatMessage(String message, User sender, ChatRoom chatRoom) {
        this.message = message;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.createdTime = LocalDateTime.now();
        chatRoom.getMessages().add(this);
    }
}