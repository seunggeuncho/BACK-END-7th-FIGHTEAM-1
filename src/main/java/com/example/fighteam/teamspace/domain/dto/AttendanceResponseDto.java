package com.example.fighteam.teamspace.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttendanceResponseDto {

    private long teamspace_id;
    private long calendar_id;
    private long user_id;
    private LocalDateTime calendar_date;
    private String att_check;
    private String etc;
    private String status;
    private int memberCnt;
    private String name;

    public AttendanceResponseDto(String name,long teamspace_id, long calendar_id, long user_id, LocalDateTime calendar_date, String att_check, String etc, String status) {
        this.name = name;
        this.teamspace_id = teamspace_id;
        this.calendar_id = calendar_id;
        this.user_id = user_id;
        this.calendar_date = calendar_date;
        this.att_check = att_check;
        this.etc = etc;
        this.status = status;
    }

    public AttendanceResponseDto() {

    }


}
