package com.example.fighteam.post.domain.dto;
//게시판 조회
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class GetBoardResponseDto {
    private String title;
    private Long user_id;
    private String typeContent;
    private String languageContent;
    private Long post_id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date enddate;
    private int count;
    private String subject;
    private int deposit;


}
