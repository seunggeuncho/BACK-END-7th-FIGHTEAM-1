package com.example.fighteam.payment.domain.dto;

import lombok.Getter;

@Getter
public class Payment {
    private int cost;
    private String type;

    public Payment(int cost, String type) {
        this.cost = cost;
        this.type = type;
    }
}
