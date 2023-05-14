package com.example.fighteam.teamspace.domain.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Deposit_history {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long history_id;
    private long user_id;
    private long teamspace_id;
    private LocalDateTime history_date;
    private String type;
    private int cost;
}
