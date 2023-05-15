package com.example.fighteam.payment.service.kakao.dto;

import lombok.Data;

@Data
public class RequestPay {
    private String tid;
    private String next_redirect_pc_url;
    private String partner_order_id; //member_id

}
