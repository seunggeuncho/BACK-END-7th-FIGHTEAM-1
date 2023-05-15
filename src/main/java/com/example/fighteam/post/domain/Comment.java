package com.example.fighteam.post.domain;

import com.example.fighteam.user.domain.repository.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * packageName    : com.example.fighteam.post.domain
 * fileName       : Comment
 * author         : jeonghwan
 * date           : 2023/05/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/12        jeonghwan       최초 생성
 */
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String comment_content;
    private LocalDateTime comment_date;
    private Integer comment_recommand;





}
