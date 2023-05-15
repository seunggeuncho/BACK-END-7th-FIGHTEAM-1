package com.example.fighteam.chat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : 김효준
 * @fileName : ChatRoomRepository
 * @since : 2023/04/26
 */
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    List<ChatRoom> findAllByUserIdOrPostUserId(Long userId, Long postId);
    Optional<ChatRoom> findByUserIdAndPostUserId(Long userId, Long postId);
}
