package com.example.fighteam.teamspace.domain.repository;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long calendar_id;
    @Column(nullable = false)
    private long teamspace_id;
    private long user_id;
    private LocalDateTime calendar_date;
    private String att_check;
    private String etc;
    private String status;
}
