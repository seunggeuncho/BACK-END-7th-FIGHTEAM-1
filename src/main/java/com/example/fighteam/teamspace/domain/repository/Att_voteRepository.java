package com.example.fighteam.teamspace.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Att_voteRepository extends JpaRepository<Att_vote,Long> {
}
