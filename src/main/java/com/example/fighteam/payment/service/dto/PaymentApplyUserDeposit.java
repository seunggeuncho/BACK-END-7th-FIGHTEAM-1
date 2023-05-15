package com.example.fighteam.payment.service.dto;

import lombok.Data;

/**
 * packageName    : com.example.fighteam.payment.service.dto
 * fileName       : paymentApplyUserDeposit
 * author         : jeonghwan
 * date           : 2023/05/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/14        jeonghwan       최초 생성
 */


@Data
public class PaymentApplyUserDeposit {
    private Long applyId;
    private int userDeposit;
}
