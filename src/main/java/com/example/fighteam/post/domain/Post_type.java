package com.example.fighteam.post.domain;

import jakarta.persistence.*;

/**
 * packageName    : com.example.fighteam.post.domain
 * fileName       : Post_type
 * author         : jeonghwan
 * date           : 2023/05/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/12        jeonghwan       최초 생성
 */
@Entity
public class Post_type {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_type_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

}
