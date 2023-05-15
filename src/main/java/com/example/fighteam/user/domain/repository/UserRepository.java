package com.example.fighteam.user.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.email=:email and u.passwd=:passwd")
    User selectUserInfo(@Param("email") String email, @Param("passwd")String passwd);

    Optional<User> findByEmail(String email);
}