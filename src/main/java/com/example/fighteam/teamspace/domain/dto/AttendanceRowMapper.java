package com.example.fighteam.teamspace.domain.dto;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class AttendanceRowMapper implements RowMapper<AttendanceResponseDto> {
    @Override
    public AttendanceResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AttendanceResponseDto attendanceResponseDto = new AttendanceResponseDto();
        attendanceResponseDto.setTeamspace_id(rs.getLong("teamspace_id"));
        attendanceResponseDto.setCalendar_id(rs.getLong("calendar_id"));
        attendanceResponseDto.setUser_id(rs.getLong("user_id"));
        Timestamp date = rs.getTimestamp("calendar_date");
        if(date == null) {
            attendanceResponseDto.setCalendar_date(null);
        } else {
            attendanceResponseDto.setCalendar_date(date.toLocalDateTime());
        }
        attendanceResponseDto.setAtt_check(rs.getString("att_check"));
        attendanceResponseDto.setEtc(rs.getString("etc"));
        attendanceResponseDto.setStatus(rs.getString("status"));
        attendanceResponseDto.setName(rs.getString("name"));
        return attendanceResponseDto;
    }
}
