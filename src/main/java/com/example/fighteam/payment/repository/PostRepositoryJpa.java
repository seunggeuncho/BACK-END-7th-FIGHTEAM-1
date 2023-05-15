package com.example.fighteam.payment.repository;

import com.example.fighteam.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.example.fighteam.payment.repository
 * fileName       : PostRepository
 * author         : jeonghwan
 * date           : 2023/05/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/11        jeonghwan       최초 생성
 */
public interface PostRepositoryJpa extends JpaRepository<Post, Long> {
}
