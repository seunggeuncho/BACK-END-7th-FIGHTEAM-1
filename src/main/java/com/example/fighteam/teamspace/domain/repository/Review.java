package com.example.fighteam.teamspace.domain.repository;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long review_id;
    private long user_rvr;
    private long user_rvd;
    private long teamspace_id;
    private int q1;
    private int q2;
    private int q3;
    private int q4;
    private int q5;
}
