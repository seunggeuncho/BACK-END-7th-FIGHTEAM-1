package com.example.fighteam.teamspace.controller;

import com.example.fighteam.teamspace.domain.dto.AttendanceResponseDto;
import com.example.fighteam.teamspace.domain.dto.AttendanceRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class TeamspaceRestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostMapping("/AttendanceEvents")
    @ResponseBody
    public ResponseEntity<List<AttendanceResponseDto>> attendanceEvents(@RequestBody Map<String, Object> param){
        Long teamspace_id = Long.valueOf(param.get("teamspace_id").toString());
        String sql = "select calendar_id, etc, teamspace_id,calendar_date, status, a.user_id, att_check, name from attendance a, users u where a.user_id = u.user_id and a.teamspace_id = ?";
        List<AttendanceResponseDto> events = jdbcTemplate.query(sql, new Object[]{teamspace_id}, new AttendanceRowMapper());
        return  ResponseEntity.ok(events);
    }

    @PostMapping("/AttendanceEventsClick")
    @ResponseBody
    public ResponseEntity<List<AttendanceResponseDto>> attendanceEventsClick(@RequestBody Map<String, Object> param){
        String teamspace_id = param.get("teamspace_id").toString();
        String date = param.get("date").toString();
        String sql = "select calendar_id, calendar_date, att_check, etc, status, teamspace_id, u.user_id, name from attendance a, users u where u.user_id = a.user_id and teamspace_id = ? and calendar_date = ?";
        List<AttendanceResponseDto> clickEvents = jdbcTemplate.query(sql, new Object[]{teamspace_id,date}, new AttendanceRowMapper());
        String sql2 = "select count(*) from apply where teamspace_id = ? group by teamspace_id";
        AttendanceResponseDto attendanceResponseDto = new AttendanceResponseDto();
        attendanceResponseDto.setMemberCnt(jdbcTemplate.queryForObject(sql2, new Object[]{teamspace_id}, Integer.class));
        clickEvents.add(attendanceResponseDto);
        return ResponseEntity.ok(clickEvents);
    }

    @PostMapping("/isMember")
    @ResponseBody
    public ResponseEntity<String> isMember(@RequestBody Map<String, Object> param){
        String teamspace_id = param.get("teamspace_id").toString();
        Long user_id = Long.valueOf(param.get("user_id").toString());
        String sql = "select status from apply where teamspace_id = ? and user_id = ?";
        String apply_status = jdbcTemplate.queryForObject(sql, new Object[]{teamspace_id,user_id},String.class);
        return ResponseEntity.ok(apply_status);
    }
    @PostMapping("/GetMembers")
    @ResponseBody
    public ResponseEntity<List<Long>> GetMembers(@RequestBody Map<String,Object> param){
        String teamspace_id = param.get("teamspace_id").toString();
        String sql = "select user_id from apply where teamspace_id = ? and status is not null";
        List<Long> members = jdbcTemplate.query(sql, new Object[]{teamspace_id}, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("user_id");
            }
        });
        return ResponseEntity.ok(members);
    }
    @GetMapping("/TeamspaceErrorManager")
    @ResponseBody
    public String TeamspaceErrorManager(@RequestParam("error_code") String error_code){
        String url="";
        if(error_code.equals("nl")){//need login
            url = "<script>alert('로그인이 필요한 서비스입니다.');location.href='http://localhost:8080/user/login';</script>";
        }else if(error_code.equals("nm")){//not member
            url = "<script>alert('해당 팀의 멤버가 아닙니다.');window.location.href='http://localhost:8080/post/home';</script>";
        }else if(error_code.equals("nmst")){//not master
            url = "<script>alert('해당 팀의 마스터권한이 필요한 서비스입니다.');window.location.href='http://localhost:8080/post/home';</script>";
        }else if(error_code.equals("et")){//exist teamspace
            url = "<script>alert('이미 생성된 팀스페이스 입니다.');window.location.href='http://localhost:8080/myPageTeamspace';</script>";
        }
        return url;
    }
}
