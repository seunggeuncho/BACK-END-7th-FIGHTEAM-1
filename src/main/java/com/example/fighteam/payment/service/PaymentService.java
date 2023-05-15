package com.example.fighteam.payment.service;

import com.example.fighteam.payment.domain.*;
import com.example.fighteam.payment.domain.dto.PayPostDto;
import com.example.fighteam.payment.repository.ApplyRepository;
import com.example.fighteam.payment.repository.HistoryRepository;
import com.example.fighteam.payment.repository.PostRepositoryJpa;
import com.example.fighteam.payment.service.dto.PaymentApplyUserDeposit;
import com.example.fighteam.post.domain.Post;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.example.fighteam.payment.service
 * fileName       : PaymentService
 * author         : jeonghwan
 * date           : 2023/05/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/05/11        jeonghwan       최초 생성
 */
@RequiredArgsConstructor
@Service
public class PaymentService {
    private final HistoryRepository historyRepository;
    private final ApplyRepository applyRepository;
    private final PostRepositoryJpa postRepositoryJpa;
    private final UserService userService;

    @Transactional
    public int payment(Long postId, Long memberId) { // 보증금 결제


        Post post = postRepositoryJpa.findById(postId).orElse(null);
        User member = userService.findUser(memberId);

        //Apply 생성
        Apply apply = new Apply(member, post, post.getDeposit(), "false");
        int cost = post.getDeposit();
        //0원 보다 작으면 에러 발생
        member.minusDeposit(cost);

        int userDeposit = member.getDeposit();
        // 위의 로직이 끝나고 에러가 없으면 apply를 DB에 저장
        applyRepository.save(apply);

        History history = new History(member, apply, HistoryType.PAYMENT, cost, userDeposit);
        historyRepository.saveHistory(history);

        return userDeposit;
    }
    @Transactional
    public PaymentApplyUserDeposit paymentPost(PayPostDto payPostDto) { // 보증금 결제


//        Post post = postRepositoryJpa.findById(postId).orElse(null);
        User member = userService.findUser(payPostDto.getUser_id());


        //Apply 생성
        Apply apply = Apply.builder()
                .user(member)
                .userDeposit(payPostDto.getDeposit())
                .status("master")
                .build();

        int cost = payPostDto.getDeposit();
        //0원 보다 작으면 에러 발생
        member.minusDeposit(cost);

        int userDeposit = member.getDeposit();
        // 위의 로직이 끝나고 에러가 없으면 apply를 DB에 저장
        Apply saveApply = applyRepository.save(apply);

        History history = new History(member, apply, HistoryType.PAYMENT, cost, userDeposit);
        historyRepository.saveHistory(history);
        PaymentApplyUserDeposit result = new PaymentApplyUserDeposit();
        result.setApplyId(saveApply.getId());
        result.setUserDeposit(userDeposit);
        return result;
    }


    @Transactional
    public void updateApplyPostId(Long applyId, Long postId) {
        Apply findApply = applyRepository.findOne(applyId);
        Post post = postRepositoryJpa.findById(postId).orElse(null);
        findApply.setPost(post);

    }
}
