package com.example.fighteam.teamspace.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamspaceRepository extends JpaRepository<Teamspace, Long> {
}
