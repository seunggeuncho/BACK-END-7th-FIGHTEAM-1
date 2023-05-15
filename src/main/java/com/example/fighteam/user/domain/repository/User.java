package com.example.fighteam.user.domain.repository;

import com.example.fighteam.chat.domain.repository.ChatRoom;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;            //userId 통일

    private String name;
    private String email;       // 로그인쓸 때 아이디
    private String passwd;
    private int score;
    private LocalDateTime joinDate;
    private int deposit;

    private String tel; // 전화번호
    private String role;        //권한을 어디서 줄지 안정했지만 일단은 관리자와 구분하기위해 추가했습니다.

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> roomList = new ArrayList<>();

    @Builder
    public User(Long id, String email, String passwd, String name, String tel) {
        this.id = id;
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.tel = tel;
        this.role = role;



    }
    public int plusDeposit(int cost) {
        this.deposit += cost;
        return this.deposit;
    }

    public int minusDeposit(int cost){
        int result = this.deposit - cost;
        validate(result);
        this.deposit = result;
        return deposit;
    }

    public void validate(int deposit) {
        if (deposit < 0){
            throw new IllegalStateException("잔액이 부족합니다.");
        }
    }

}



