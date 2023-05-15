package com.example.fighteam.post.domain.dto;

import lombok.Getter;

@Getter
public class PageDto {
    private int startPage;
    private int endPage;
    private boolean prev;

    private boolean next;

    private int total;

    private int pageNum;    //페이지 번호

    private int amount; //한 페이지당 글개수

    public PageDto(int pageNum, int amount, int total){
        this.endPage = (int) Math.ceil(pageNum / 5.0) * 5;

        this.startPage = this.endPage - (5 -1);

        int realEnd = (int)Math.ceil((double) total/ amount);

        if(realEnd < this.endPage){
            this.endPage = realEnd;
        }
        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}
