package com.example.fighteam.teamspace.controller;

import com.example.fighteam.apply.domain.dto.ApplyResponseDto;
import com.example.fighteam.apply.service.ApplyService;
import com.example.fighteam.post.service.PostService;
import com.example.fighteam.teamspace.domain.dto.AttendanceCheckRequestDto;
import com.example.fighteam.teamspace.domain.dto.AttendanceResponseDto;
import com.example.fighteam.teamspace.domain.dto.HistoryDto;
import com.example.fighteam.teamspace.domain.dto.TeamspaceMyPageResponseDto;
import com.example.fighteam.teamspace.service.TeamspaceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TeamspaceController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private TeamspaceService teamspaceService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PostService postService;

    @GetMapping("/attendanceMain")
    public String attendanceMain(@RequestParam("teamspace_id") Long teamspace_id,HttpSession session, Model model){
        String url;

        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else {
            Long session_user_id = Long.valueOf(session.getAttribute("loginId").toString());
            if(teamspaceService.isMemberByTsid(session_user_id,teamspace_id)){
                Long user_id = Long.valueOf(session.getAttribute("loginId").toString());
                List<AttendanceResponseDto> members = teamspaceService.getMember(teamspace_id);
                List<HistoryDto> history = teamspaceService.getHistory(user_id, teamspace_id);
                AttendanceCheckRequestDto attendanceCheckRequestDto = new AttendanceCheckRequestDto();
                int UserDeposit = teamspaceService.getUserDeposit(user_id, teamspace_id);
                String teamspace_name = teamspaceService.getTeamspaceName(teamspace_id,"teamspace_id");
                model.addAttribute("teamspace_name",teamspace_name+" - 팀 스페이스");
                model.addAttribute("teamspace_id", teamspace_id);
                //model.addAttribute("radioValues",new String[members.size()]);
                model.addAttribute("userDeposit", UserDeposit);
                model.addAttribute("depositTitle", "현재 보증금 : "+UserDeposit);
                model.addAttribute("history", history);
                model.addAttribute("members",members);
                model.addAttribute("memCnt",members.size());
                model.addAttribute("attendanceCheck",attendanceCheckRequestDto);
                //List<Deposit> list2 = depositService.getDeposit(user_id,Teamspcae_id);
                url = "teamspace/AttendanceMain";
            }else{
                url = "redirect:/TeamspaceErrorManager?error_code=nm"; //not member
            }
        }
        return url;
    }

    @PostMapping("/attendanceWrite")
    public String attendanceWritePro(@ModelAttribute("attendanceCheck") AttendanceCheckRequestDto attendanceCheckRequestDto){

        if(attendanceCheckRequestDto.getForm_type().equals("c")){//출석 이벤트 생성
            teamspaceService.writeAttendance(attendanceCheckRequestDto, attendanceCheckRequestDto.getWriter());
        }else if(attendanceCheckRequestDto.getForm_type().equals("u")) {//출석 이벤트 업데이트
            teamspaceService.updateAttendance(attendanceCheckRequestDto);
        }else if(attendanceCheckRequestDto.getForm_type().equals("v")){//출석 이벤트 투표
            teamspaceService.attVote(attendanceCheckRequestDto);
            teamspaceService.voteConfirmCheck(attendanceCheckRequestDto);
        }else if(attendanceCheckRequestDto.getForm_type().equals("d")){//출석 이벤트 삭제

        }
        return "redirect:/attendanceMain?teamspace_id="+attendanceCheckRequestDto.getTeamspace_id();
    }

    @GetMapping(value = "/CreateTeamspacePro"/*,consumes = "application/x-www-form-urlencoded"*/)
    public String CreateTeamspacePro(@RequestParam("post_id") String post_id,@RequestParam("teamspace_name") String teamspace_name,
                                     @RequestParam(value = "sub_master", required = false, defaultValue = "0")Long sub_master, Model model,HttpSession session){
        if(sub_master == 0){
            return "redirect:/TeamspaceErrorManager?error_code=ns";//need sub_master
        }
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else{
            Long session_user_id = Long.valueOf(session.getAttribute("loginId").toString());
            if(teamspaceService.isMaster(session_user_id,Long.valueOf(post_id))){
                if(!teamspaceService.TeamspaceIsExistByPost(post_id)){
                    Long master = teamspaceService.getMasterFromApply(post_id);
                    String teamspace_id = teamspaceService.CreateTeamspace(post_id,master,sub_master,teamspace_name);
                    teamspaceService.AppointSub(sub_master,post_id);
                    postService.completepost(Long.valueOf(post_id));
                    url = "redirect:/attendanceMain?teamspace_id="+teamspace_id;
                }else{
                    url = "redirect:/TeamspaceErrorManager?error_code=et";//exist teamspace
                }
            }else{
                url = "redirect:/TeamspaceErrorManager?error_code=nmst"; //not master
            }
        }
        return url;
    }

    @GetMapping("/CreateTeamspace")
    public String CreateTeamspace(@RequestParam("post_id") Long post_id,Model model, HttpSession session){
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else{
            Long session_user_id = Long.valueOf(session.getAttribute("loginId").toString());
            if(teamspaceService.isMaster(session_user_id,post_id)){
                if(!teamspaceService.TeamspaceIsExistByPost(post_id.toString())){//배포시 조건에!
                    List<ApplyResponseDto> apply_List = applyService.getApplyList(post_id);
                    model.addAttribute("applyList",apply_List);
                    model.addAttribute("post_id", post_id);
                    url = "teamspace/CreateTeamspace";
                }else{
                    url = "redirect:/TeamspaceErrorManager?error_code=et";//exist teamspace
                }
            }else{
                url = "redirect:/TeamspaceErrorManager?error_code=nmst"; //not master
            }
        }
        return url;
    }
    @PostMapping(value="/AcceptApply",consumes = "application/x-www-form-urlencoded")
    public String AcceptApply(@RequestParam("user_id") Long user_id,@RequestParam("post_id") Long post_id, HttpSession session){
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else{
            Long session_user_id = Long.valueOf(session.getAttribute("loginId").toString());
            if(teamspaceService.isMaster(session_user_id,post_id)){
                url = "redirect:/CreateTeamspace?post_id="+post_id;
                applyService.memberAccept(user_id,post_id);
            }else{
                url = "redirect:/TeamspaceErrorManager?error_code=nmst"; //not master
            }
        }
        return url;
    }
//여기까지
    @PostMapping(value = "/DenyApply",consumes = "application/x-www-form-urlencoded")
    public String DenyApply(@RequestParam("user_id") Long user_id,@RequestParam("post_id") Long post_id, HttpSession session){
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else{
            Long session_user_id = Long.valueOf(session.getAttribute("loginId").toString());
            if(teamspaceService.isMaster(session_user_id,post_id)){
                url = "redirect:/CreateTeamspace?post_id="+post_id;
                applyService.memberDeny(user_id,post_id);
            }else{
                url = "redirect:/TeamspaceErrorManager?error_code=nmst"; //not master
            }
        }
        return url;
    }

    @GetMapping("/ReviewTeammates")
    public String ReviewTeammates(@RequestParam("teamspace_id") Long teamspace_id,Model model, HttpSession session){
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else{
            Long user_id = Long.valueOf(session.getAttribute("loginId").toString());
            if(teamspaceService.isMemberByTsid(user_id,teamspace_id)){
                List<ApplyResponseDto> apply_List = applyService.getApplyListByTsid(teamspace_id);
                for(int i = 0; i < apply_List.size(); i++){
                    System.out.println(apply_List.get(i).getUser_id());
                }
                List<Long> rvd_list = teamspaceService.getReviewList(teamspace_id,user_id);
                model.addAttribute("applyList", apply_List);
                model.addAttribute("rvd_list",rvd_list);
                model.addAttribute("teamspace_id",teamspace_id);
                model.addAttribute("user_id", user_id);
                url = "teamspace/ReviewTeammates";
            }else{
                url = "redirect:/TeamspaceErrorManager?error_code=nm"; //not member
            }
        }
        return url;
    }
    @PostMapping("/ReviewWrite")
    public String ReviewWrite(@RequestParam("teamspace_id") Long teamspace_id,@RequestParam("user_id") Long user_id,
                              @RequestParam("review_q1") int q1,@RequestParam("review_q2") int q2,
                              @RequestParam("review_q3") int q3,@RequestParam("review_q4") int q4,
                              @RequestParam("review_q5") int q5,HttpSession session){
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else{
            Long session_user_id = Long.valueOf(session.getAttribute("loginId").toString());
            System.out.println(teamspaceService.isMemberByTsid(session_user_id,teamspace_id));
            int row = teamspaceService.writeReview(teamspace_id, Long.valueOf(session.getAttribute("loginId").toString()),user_id,q1,q2,q3,q4,q5);
            url = "redirect:/ReviewTeammates?teamspace_id="+teamspace_id;
        }
        return url;
    }

    @GetMapping("/myPageTeamspace")
    public String myPageTeamspace(HttpSession session,Model model){
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else{
            Long user_id = Long.valueOf(session.getAttribute("loginId").toString());
            List<TeamspaceMyPageResponseDto> team_list = teamspaceService.myPageTeamspaceList(user_id);
            model.addAttribute("team_list",team_list);
            url = "teamspace/myPageTeamspace";
        }
        return url;
    }
    @GetMapping("/ChangeStatusReview")
    public String ChangeStatusReview(@RequestParam("teamspace_id") String teamspace_id, HttpSession session){
        String url = "";
        if(session.getAttribute("loginId") == null){
            url = "redirect:/TeamspaceErrorManager?error_code=nl"; //need login
        }else {
            Long session_user_id = Long.valueOf(session.getAttribute("loginId").toString());
            if (teamspaceService.isMemberByTsid(session_user_id, Long.valueOf(teamspace_id))) {
                teamspaceService.StatusChangeToReview(teamspace_id);
                url = "redirect:/myPageTeamspace";
            } else {
                url = "redirect:/TeamspaceErrorManager?error_code=nm"; //not member
            }
        }
        return url;
    }
}
