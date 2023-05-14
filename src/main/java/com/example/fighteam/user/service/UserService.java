package com.example.fighteam.user.service;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(User user){
        user.setRole("USER");
        user.setJoinDate(LocalDateTime.now());
        User save = userRepository.save(user);
        System.out.println("saveUser = " + save);
    }

    public User loginUser(String email, String passwd){
        User user = userRepository.selectUserInfo(email,passwd);
        return user;
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    public User editUser(String email){
         User user = userRepository.editUserInfo(email);
         return user;
    }


}

