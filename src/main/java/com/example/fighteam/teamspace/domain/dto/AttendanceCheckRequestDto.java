package com.example.fighteam.teamspace.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceCheckRequestDto {

    private String[] user_id;
    private String teamspace_id;
    private String etc;
    private String calendar_date;
    private String form_type;
    private String writer;
    private String[] att_checks;

    public AttendanceCheckRequestDto(String writer,String form_type, String[] user_id,String teamspace_id, String etc, String calendar_date, String[] att_checks) {
        this.writer = writer;
        this.form_type = form_type;
        this.user_id = user_id;
        this.teamspace_id = teamspace_id;
        this.etc = etc;
        this.calendar_date = calendar_date;
        this.att_checks = att_checks;
    }
    public AttendanceCheckRequestDto(){
    }

}
