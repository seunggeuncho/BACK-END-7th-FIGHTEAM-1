package com.example.fighteam.post.domain;

import com.example.fighteam.user.domain.repository.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "post_table")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    @Column(length = 3000)
    private String content;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private int recruitdate;
    private int deposit;
    @CreatedDate
    private LocalDateTime date;
    private int count;
    private String complete;
    private String subject;
}
