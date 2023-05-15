package com.example.fighteam.teamspace.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TeamspaceMyPageResponseDto {
    private String apply_status;
    private String teamspace_id;
    private Timestamp start_date;
    private Timestamp end_date;
    private String teamspace_status;
    private Long post_id;
    public TeamspaceMyPageResponseDto(Long post_id,String apply_status, String teamspace_id, String teamspace_status,Timestamp start_date, Timestamp end_date){
        this.apply_status = apply_status;
        this.teamspace_id = teamspace_id;
        this.teamspace_status = teamspace_status;
        this.end_date = end_date;
        this.start_date = start_date;
        this.post_id = post_id;
    }
    public TeamspaceMyPageResponseDto(){}

}
