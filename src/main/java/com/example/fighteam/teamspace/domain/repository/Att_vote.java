package com.example.fighteam.teamspace.domain.repository;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Att_vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long vote_id;
    private long teamspace_id;
    private long user_id;
    private LocalDateTime calendar_date;
    private String vote;
}
