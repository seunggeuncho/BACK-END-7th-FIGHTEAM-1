package com.example.fighteam.post.domain;

import jakarta.persistence.*;

/**
 * packageName    : com.example.fighteam.post.domain
 * fileName       : Type
 * author         : jeonghwan
 * date           : 2023/05/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/12        jeonghwan       최초 생성
 */

@Entity
public class Type {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long id;
    private String type_content;


}
