package com.example.fighteam.chat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : 김효준
 * @fileName : ChatMessageRepository
 * @since : 2023/04/30
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
}
