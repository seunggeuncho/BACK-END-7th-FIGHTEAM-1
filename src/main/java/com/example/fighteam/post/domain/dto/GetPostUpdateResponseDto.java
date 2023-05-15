package com.example.fighteam.post.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class GetPostUpdateResponseDto {
    private Long post_id;
    private int count;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startdate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date enddate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    private int recruitdate;      //개발 기간
    private int deposit;
    private List<String> typeContent;   //모집 포지션
    private List<String> languageContent;   //기술 스택
    private String title;
    private String content;
    private Long user_id;
    private String subject;

    public static GetPostUpdateResponseDto setGetPostUpdateResponseDtd(GetPostDetailResponseDto g, List<String> language, List<String> type){
        GetPostUpdateResponseDto getPostUpdateResponseDto = new GetPostUpdateResponseDto();
        getPostUpdateResponseDto.setPost_id(g.getPost_id());
        getPostUpdateResponseDto.setCount(g.getCount());
        getPostUpdateResponseDto.setStartdate(g.getStartdate());
        getPostUpdateResponseDto.setEnddate(g.getEnddate());
        getPostUpdateResponseDto.setDate(g.getDate());
        getPostUpdateResponseDto.setRecruitdate(g.getRecruitdate());
        getPostUpdateResponseDto.setDeposit(g.getDeposit());
        getPostUpdateResponseDto.setTitle(g.getTitle());
        getPostUpdateResponseDto.setContent(g.getContent());
        getPostUpdateResponseDto.setUser_id(g.getUser_id());
        getPostUpdateResponseDto.setSubject(g.getSubject());
        getPostUpdateResponseDto.setLanguageContent(language);
        getPostUpdateResponseDto.setTypeContent(type);
        return getPostUpdateResponseDto;
    }
}
