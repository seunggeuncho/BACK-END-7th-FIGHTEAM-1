package com.example.fighteam.payment.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.example.fighteam.payment.domain.dto
 * fileName       : PaymentDto
 * author         : jeonghwan
 * date           : 2023/05/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/13        jeonghwan       최초 생성
 */
@Data
public class PaymentDto {
    private Long postId;
    private Long userId;
    private String postTitle;
    private int postDeposit;
    private int userDeposit;

    @Builder
    public PaymentDto(Long postId, Long userId, String postTitle, int postDeposit, int userDeposit) {
        this.postId = postId;
        this.userId = userId;
        this.postTitle = postTitle;
        this.postDeposit = postDeposit;
        this.userDeposit = userDeposit;
    }
}
