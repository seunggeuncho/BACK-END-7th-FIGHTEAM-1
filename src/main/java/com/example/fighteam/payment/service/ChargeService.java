package com.example.fighteam.payment.service;

import com.example.fighteam.payment.domain.History;
import com.example.fighteam.payment.domain.HistoryType;
import com.example.fighteam.payment.repository.ApplyRepository;
import com.example.fighteam.payment.repository.HistoryRepository;
import com.example.fighteam.payment.repository.PostRepositoryJpa;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChargeService {
    private final HistoryRepository historyRepository;
    private final ApplyRepository applyRepository;
    private final PostRepositoryJpa postRepository;
    private final UserRepository userRepository;



    @Transactional
    public int chargeDeposit(Long id, int cost){ //보증금 충전

//        Member findMember = memberRepository.findMember(id);
        User findUser = userRepository.findById(id).orElse(null);
//        int deposit = findMember.getDeposit();
        int deposit = findUser.getDeposit();
        deposit += cost;
        findUser.setDeposit(deposit);

        History history = new History(findUser, HistoryType.CHARGE, cost, deposit);
        historyRepository.saveHistory(history);
        //DTO에 member id, type, cost, 입금날짜, 잔액 담아서 controller로 반환

        return deposit;

    }




}
