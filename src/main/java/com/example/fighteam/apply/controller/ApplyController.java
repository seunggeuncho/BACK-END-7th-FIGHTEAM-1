package com.example.fighteam.apply.controller;

import com.example.fighteam.apply.domain.dto.ApplyResponseDto;
import com.example.fighteam.apply.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @GetMapping("/ApplyTeamPage")
    public String ApplyTeamPage (@RequestParam("post_id") Long post_id, Model model){
        List<ApplyResponseDto> apply_List = applyService.getApplyList(post_id);
        model.addAttribute("applyList", apply_List);
//        GetPostDetailResponseDto getPostDetailResponseDto;
//        List<GetCommentDto> getCommentDto;
//        getPostDetailResponseDto = postService.findById(post_id);
//        model.addAttribute("board", getPostDetailResponseDto);
//

        return "teamspace/ApplyMain";
    }

}
