package com.example.fighteam.payment.domain;


import com.example.fighteam.post.domain.Post;
import com.example.fighteam.teamspace.domain.repository.Teamspace;
import com.example.fighteam.user.domain.repository.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter

public class Apply {

    @Id @GeneratedValue
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamspace_id")
    private Teamspace teamspace;

    private int userDeposit;

    
    private String status;

    public Apply() {
    }

    @Builder
    public Apply(User user, int userDeposit, String status) {
        this.user = user;
        this.userDeposit = userDeposit;
        this.status = status;
    }

    public Apply(User user, Post post, int userDeposit, String status) {
        this.user = user;
        this.post = post;
        this.userDeposit = userDeposit;
        this.status = status;
    }

    public void plusUserDeposit(int cost) {
        this.userDeposit += cost;
    }

    public void minusUserDeposit(int cost) {
        int result = this.userDeposit - cost;
        validate(result);
        this.userDeposit = result;
    }

    public void validate(int deposit) {
        if(deposit < 0){
            throw new IllegalStateException("잔액이 부족합니다.");
        }
    }
}
