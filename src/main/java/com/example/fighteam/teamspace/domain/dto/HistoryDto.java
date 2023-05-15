package com.example.fighteam.teamspace.domain.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class HistoryDto {
    private long user_id;
    private String history_date;
    private String type;
    private int cost;
    private int balance;


    public HistoryDto(int balance, long user_id, String history_date, String type, int cost){
        this.balance = balance;
        this.history_date = history_date;
        this.cost = cost;
        this.type = type;
        this.user_id = user_id;
    }
    public HistoryDto(){}

}
