package com.example.fighteam.payment.domain;

import com.example.fighteam.user.domain.repository.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class History {

    @Id
    @GeneratedValue
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    private Apply apply;



    @Enumerated(EnumType.STRING)
    private HistoryType type;

    private int cost;

    private LocalDateTime date;

    private int balance;

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                ", balance=" + balance +
                '}';
    }

    public History() {}


    public History(User member, Apply apply, HistoryType type, int cost, int balance) {
        this(member, type, cost, balance);
        this.apply = apply;

    }

    @Builder
    public History(User member,  HistoryType type, int cost,  int balance) {
        this.user = member;
        this.type = type;
        this.cost = cost;
        this.balance = balance;
        this.date = LocalDateTime.now();
    }
}
