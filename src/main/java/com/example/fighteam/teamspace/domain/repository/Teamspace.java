package com.example.fighteam.teamspace.domain.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Teamspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamspace_id;
    private long post_id;
    private String teamspace_name;
    private long master;
    private long sub_master;
    private String status;
}
