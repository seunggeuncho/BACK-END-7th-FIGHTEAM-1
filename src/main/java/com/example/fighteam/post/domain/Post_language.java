package com.example.fighteam.post.domain;

import com.example.fighteam.post.domain.Language;
import com.example.fighteam.post.domain.Post;
import jakarta.persistence.*;

/**
 * packageName    : com.example.fighteam.post
 * fileName       : Post_language
 * author         : jeonghwan
 * date           : 2023/05/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/12        jeonghwan       최초 생성
 */
@Entity
public class Post_language {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_language_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;
}
