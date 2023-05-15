package com.example.fighteam.teamspace.service;

import com.example.fighteam.payment.service.PenaltyService;
import com.example.fighteam.teamspace.domain.dto.AttendanceCheckRequestDto;
import com.example.fighteam.teamspace.domain.dto.AttendanceResponseDto;
import com.example.fighteam.teamspace.domain.dto.HistoryDto;
import com.example.fighteam.teamspace.domain.dto.TeamspaceMyPageResponseDto;
import com.example.fighteam.teamspace.domain.repository.AttendanceRepository;
import com.example.fighteam.teamspace.domain.repository.TeamspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TeamspaceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TeamspaceRepository teamspaceRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PenaltyService penaltyService;
    public void createAttendance(AttendanceCheckRequestDto attendanceCheckRequestDto) {

    }
    public int getUserDeposit(Long user_id, Long teamspace_id){
        String sql = "select user_deposit from apply where user_id = ? and teamspace_id = ?";
        int UserDeposit = jdbcTemplate.queryForObject(sql, new Object[]{user_id, teamspace_id}, Integer.class);
        return UserDeposit;
    }

    public List<HistoryDto> getHistory(Long user_id, Long teamspace_id) {
        String sql = "select * from deposit_history where user_id = ? and teamspace_id = ?";
        List<HistoryDto> history = jdbcTemplate.query(sql, new Object[]{ user_id,teamspace_id}, new RowMapper<HistoryDto>() {
            @Override
            public HistoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                HistoryDto historyDto = new HistoryDto();
                historyDto.setHistory_date(sdf.format(rs.getTimestamp("history_date")));
                historyDto.setCost(rs.getInt("cost"));
                historyDto.setType(rs.getString("type"));
                historyDto.setUser_id(rs.getLong("user_id"));

                return historyDto;
            }
        });
        return history;
    }

    public String getTeamspaceName(Long teamspace_id, String by){
        String sql = "select teamspace_name from teamspace where teamspace_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{teamspace_id}, String.class);
    }
    public int writeAttendance(AttendanceCheckRequestDto attendanceCheckRequestDto,String user_id){
        String sql = "insert into attendance( teamspace_id, user_id, calendar_date, att_check, etc, status)" +
                "values(?, ?, ?, ?, ?, ?)";
        Long teamspace_id = Long.parseLong(attendanceCheckRequestDto.getTeamspace_id());
        String[] users_id = attendanceCheckRequestDto.getUser_id();
        int rowCnt = 0;
        for(int i = 0; i < users_id.length; i++){
            rowCnt += jdbcTemplate.update(sql,teamspace_id, users_id[i],attendanceCheckRequestDto.getCalendar_date(),
                    attendanceCheckRequestDto.getAtt_checks()[i],attendanceCheckRequestDto.getEtc(),"unconfirm");
        }
        String vote_sql = "insert into att_vote( teamspace_id, user_id, calendar_date, vote) " +
                "values( ?,?,?,'agree')";
        jdbcTemplate.update(vote_sql, attendanceCheckRequestDto.getTeamspace_id(),user_id,attendanceCheckRequestDto.getCalendar_date());
        return rowCnt;
    }
    public int updateAttendance(AttendanceCheckRequestDto attendanceCheckRequestDto){
        String sql = "select count(user_id) from attendance where user_id = ? and teamspace_id = ? and calendar_date = ?";
        String[] users_id = attendanceCheckRequestDto.getUser_id();
        String[] att_check = attendanceCheckRequestDto.getAtt_checks();
        Long teamspace_id = Long.parseLong(attendanceCheckRequestDto.getTeamspace_id());
        int rowCnt = 0;
        for(int i = 0; i < users_id.length; i++){
            Long member_id = Long.parseLong(users_id[i]);
            int result = jdbcTemplate.queryForObject(sql, new Object[]{member_id, teamspace_id,
                    attendanceCheckRequestDto.getCalendar_date()}, Integer.class);
            if(result == 1){
                String u_sql = "update attendance set att_check = ?, etc = ? where user_id = ? and teamspace_id = ? and calendar_date = ?";
                rowCnt += jdbcTemplate.update(u_sql,att_check[i],attendanceCheckRequestDto.getEtc(),
                        member_id,teamspace_id, attendanceCheckRequestDto.getCalendar_date());
            }else{
                String i_sql = "insert into attendance( teamspace_id, user_id, calendar_date, att_check, etc, status)" +
                        "values( ?, ?, ? , ?, ?, ?);";
                rowCnt += jdbcTemplate.update(sql,teamspace_id, member_id,attendanceCheckRequestDto.getCalendar_date(),
                        att_check[i],attendanceCheckRequestDto.getEtc(),'N');
            }
        }
        return rowCnt;
    }
    public List<AttendanceResponseDto> getMember(long teamspace_id){
        String sql = "select a.user_id, name from apply a, users u where teamspace_id = ? and a.user_id = u.user_id";
        List<AttendanceResponseDto> members= jdbcTemplate.query(sql, new Object[]{teamspace_id}, new RowMapper<AttendanceResponseDto>() {
            @Override
            public AttendanceResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                AttendanceResponseDto attendanceResponseDto = new AttendanceResponseDto();
                attendanceResponseDto.setUser_id(rs.getLong("user_id"));
                attendanceResponseDto.setName(rs.getString("name"));
                return attendanceResponseDto;
            }
        });
        return  members;
    }
    public String CreateTeamspace(String post_id, Long master, Long sub_master,String teamspace_name){
        String delete_sql = "delete from apply where post_id = ? and status = 'false'";
        jdbcTemplate.update(delete_sql,post_id);
        String insert_sql = "insert into teamspace( post_id, teamspace_name, master, sub_master,status) values( ?, ?, ?,?,'running')";
        jdbcTemplate.update(insert_sql, post_id, teamspace_name, master, sub_master);
        String select_sql = "select  teamspace_id from teamspace where post_id = ?";
        String teamspace_id = jdbcTemplate.queryForObject(select_sql, new Object[]{post_id}, String.class);
        String update_sql = "update apply set teamspace_id = ? where post_id = ?";
        jdbcTemplate.update(update_sql,teamspace_id,post_id);
        return teamspace_id;
    }
    public int StatusChangeToReview(String teamspace_id){
        String change_sql = "update teamspace set status = 'review' where teamspace_id = ?";
        String get_post_id = "select post_id from teamspace where teamspace_id = ?";

        penaltyService.returnUserDeposit(jdbcTemplate.queryForObject(get_post_id,new Object[]{teamspace_id},Long.class));
        return jdbcTemplate.update(change_sql,teamspace_id);
    }
    public boolean isMaster(Long user_id, Long post_id){
        String sql = "select status from apply where user_id = ? and post_id = ?";
        String status = null;
        try{
            status= jdbcTemplate.queryForObject(sql, new Object[]{user_id, post_id}, String.class);
        }catch (EmptyResultDataAccessException e){
            status = null;
        }
        if(status != null && status.equals("master")){
            return true;
        }else{
            return false;
        }
    }
    public boolean isMember(Long user_id, Long post_id){
        String sql = "select status from apply where user_id = ? and post_id = ?";
        String status= null;
        try{
            status= jdbcTemplate.queryForObject(sql, new Object[]{user_id, post_id}, String.class);
        }catch (EmptyResultDataAccessException e){
            status = null;
        }
        if(status == null){
            return false;
        }else{
            return true;
        }
    }
    public boolean isMemberByTsid(Long user_id, Long teamspace_id){
        String sql = "select status from apply where user_id = ? and teamspace_id = ?";
        String status = null;
        try{
            status= jdbcTemplate.queryForObject(sql, new Object[]{user_id, teamspace_id}, String.class);
        }catch (EmptyResultDataAccessException e){
            status = null;
        }
        System.out.println(status);
        if(status != null){
            return true;
        }else{
            return false;
        }
    }
    public List<Long> getReviewList(Long teamspace_id, Long user_id){
        String sql = "select user_rvd from review where teamspace_id = ? and user_rvr = ?";
        List<Long> list = jdbcTemplate.query(sql, new Object[]{teamspace_id, user_id}, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("user_rvd");
            }
        });
        return list;
    }
    public int writeReview(Long teamspace_id, Long user_rvr, Long user_rvd, int q1,int q2, int q3, int q4, int q5){
        String sql = "insert into review(user_rvr, user_rvd, teamspace_id, q1,q2,q3,q4,q5) " +
                "values( ?,?,?,?,?,?,?,?)";
        String sql2 = "update users set score = score + ? where user_id = ?";
        int rowCnt = jdbcTemplate.update(sql,user_rvr,user_rvd,teamspace_id,q1,q2,q3,q4,q5);
        jdbcTemplate.update(sql2,(q1+q2+q3+q4+q5)/5,user_rvd);
        return rowCnt;
    }

    public int attVote(AttendanceCheckRequestDto attendanceCheckRequestDto){
        String sql = "insert into att_vote(teamspace_id, user_id, calendar_date, vote) values( ?,?,?,'agree')";
        return jdbcTemplate.update(sql,attendanceCheckRequestDto.getTeamspace_id(),attendanceCheckRequestDto.getWriter(),attendanceCheckRequestDto.getCalendar_date());
    }

    public void voteConfirmCheck(AttendanceCheckRequestDto attendanceCheckRequestDto){
        String sql = "update attendance set status = 'confirm' where teamspace_id = ? and FORMATDATETIME(calendar_date, 'yyyy-MM-dd') = ? and (select count(user_id) from att_vote where teamspace_id = ? and  FORMATDATETIME(calendar_date, 'yyyy-MM-dd') = ? and vote = 'agree') > (select count(user_id) from apply where teamspace_id = ?)/2";
        String teamspace_id = attendanceCheckRequestDto.getTeamspace_id();
        String calendar_date = attendanceCheckRequestDto.getCalendar_date();
        jdbcTemplate.update(sql, teamspace_id, calendar_date, teamspace_id,calendar_date,teamspace_id);
        String sql4 = "select * from attendance where teamspace_id = ? and FORMATDATETIME(calendar_date, 'yyyy-MM-dd') = ?";
        String sql_getapply = "select apply_id from apply where user_id = ? and teamspace_id =?";
        String sql_history_insert = "insert into DEPOSIT_HISTORY(user_id, teamspace_id, history_date, type, cost) values(?,?,current_date(),?,?)";
        String sql_histroy_get = "insert into deposit_history(user_id, teamspace_id, history_date, type, cost) values(?,?,current_date(), ?,?)";
        List<AttendanceResponseDto> attend_list = jdbcTemplate.query(sql4, new Object[]{teamspace_id, calendar_date}, new RowMapper<AttendanceResponseDto>() {
            @Override
            public AttendanceResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                AttendanceResponseDto attendanceResponseDto = new AttendanceResponseDto();
                attendanceResponseDto.setUser_id(rs.getLong("user_id"));
                attendanceResponseDto.setAtt_check(rs.getString("att_check"));
                return attendanceResponseDto;
            }
        });
        int cost = 0;
        for(int i = 0; i < attend_list.size(); i++){
            if(attend_list.get(i).getAtt_check().equals("late")){
                cost = 1000;
            }else if(attend_list.get(i).getAtt_check().equals("absence")){
                cost = 1500;
            }
            if(!attend_list.get(i).getAtt_check().equals("attend")){
                Long apply_id = jdbcTemplate.queryForObject(sql_getapply, new Object[]{attend_list.get(i).getUser_id(), teamspace_id}, Long.class);
                penaltyService.penaltyLogic(apply_id,cost);
                jdbcTemplate.update(sql_history_insert, attend_list.get(i).getUser_id(),teamspace_id, attend_list.get(i).getAtt_check(),cost*-1);
                for(int x = 0; x < attend_list.size(); x++){
                    if(x != i){
                        jdbcTemplate.update(sql_histroy_get,attend_list.get(x).getUser_id(),teamspace_id, "get",cost/(attend_list.size()-1));
                    }
                }
            }
        }
    }
    public List<TeamspaceMyPageResponseDto> myPageTeamspaceList(Long user_id){
        String sql1 = "select status, post_id, teamspace_id from apply where user_id = ?";
        List<TeamspaceMyPageResponseDto> team_list = jdbcTemplate.query(sql1, new Object[]{user_id}, new RowMapper<TeamspaceMyPageResponseDto>() {
            @Override
            public TeamspaceMyPageResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                TeamspaceMyPageResponseDto teamspaceMyPageResponseDto = new TeamspaceMyPageResponseDto();
                teamspaceMyPageResponseDto.setApply_status(rs.getString("status"));
                teamspaceMyPageResponseDto.setPost_id(rs.getLong("post_id"));
                if(String.valueOf(rs.getLong("teamspace_id")).equals("0")){
                    teamspaceMyPageResponseDto.setTeamspace_status("wait");//시작 준비
                    teamspaceMyPageResponseDto.setTeamspace_id(String.valueOf(rs.getLong("teamspace_id")));
                }else{
                    teamspaceMyPageResponseDto.setTeamspace_id(String.valueOf(rs.getLong("teamspace_id")));
                    String sql2 = "select status from teamspace where teamspace_id = ? ";
                    String team_status = jdbcTemplate.queryForObject(sql2, new Object[]{String.valueOf(rs.getLong("teamspace_id"))},String.class);
                    teamspaceMyPageResponseDto.setTeamspace_status(team_status);//진행중
                }//db생성시 변경
                return teamspaceMyPageResponseDto;
            }
        });
        return team_list;
    }
    public String getTeamspaceStatus(Long teamspace_id){
        String sql = "select teamspace_status from teamspace where teamspace_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{teamspace_id}, String.class);
    }
    public Long getMasterFromApply(String post_id){
        String sql = "select user_id from apply where post_id = ? and status = 'master'";
        return jdbcTemplate.queryForObject(sql,new Object[]{post_id}, Long.class);
    }
    public boolean TeamspaceIsExistByPost(String post_id){
        String sql = "select teamspace_id from teamspace where post_id = ?";
        String tsid = null;
        try{
            tsid = jdbcTemplate.queryForObject(sql, new Object[]{post_id}, String.class);
        }catch (EmptyResultDataAccessException e){
            tsid = null;
        }

        if(tsid==null){
            return false;
        }else{
            return true;
        }
    }
    public void AppointSub(Long sub_master,String post_id){
        String sql = "UPDATE apply SET status ='sub_master'  WHERE post_id=? and user_id=?";
        jdbcTemplate.update(sql,post_id,sub_master);
    }
    public void AttendanceDelete(AttendanceCheckRequestDto attendanceCheckRequestDto){
        String sql = "delete from attendance where teamspace_id = ? and calendar_date";
        jdbcTemplate.update(sql,attendanceCheckRequestDto.getTeamspace_id(),attendanceCheckRequestDto.getCalendar_date());
        String sql2 = "delete from att_vote where teamspace_id = ? and calendar_date";
        jdbcTemplate.update(sql,attendanceCheckRequestDto.getTeamspace_id(),attendanceCheckRequestDto.getCalendar_date());
    }

}
