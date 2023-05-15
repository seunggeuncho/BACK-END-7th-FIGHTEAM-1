package com.example.fighteam.payment.controller;

import com.example.fighteam.apply.service.ApplyService;
import com.example.fighteam.payment.domain.dto.PayPostDto;
import com.example.fighteam.payment.domain.dto.PaymentDto;
import com.example.fighteam.payment.service.dto.PaymentApplyUserDeposit;
import com.example.fighteam.post.domain.Post;
import com.example.fighteam.payment.repository.PostRepositoryJpa;

import com.example.fighteam.payment.service.ChargeService;
import com.example.fighteam.payment.service.PaymentService;
import com.example.fighteam.post.domain.dto.CreatePostDto;
import com.example.fighteam.post.service.PostService;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PostRepositoryJpa postRepository;
    private final PaymentService paymentService;
    private final UserService userService;
    private final ChargeService chargeService;
    private final PostService postService;
    private final ApplyService applyService;


    @PostMapping("/payment/post")
    public String payment(@ModelAttribute("payPostDto") PayPostDto payPostDto, Model model, HttpSession session) {

        Long loginId = (Long) session.getAttribute("loginId");
        User user = userService.findUser(loginId);

        payPostDto.setUser_id(user.getId());
        payPostDto.setUserDeposit(user.getDeposit());
        model.addAttribute("errorMessage", null);


        return "payment/member/payment/paymentPostForm";

    }


    @PostMapping("/payment")
    public String payment(@RequestParam("post_id") Long postId, HttpSession session, Model model) {

        Long id = (Long) session.getAttribute("loginId");

        Post findPost = postRepository.findById(postId).orElse(null);
        User findUser = userService.findUser(id);
        PaymentDto paymentDto = PaymentDto.builder()
                        .postId(findPost.getId())
                        .postDeposit(findPost.getDeposit())
                        .postTitle(findPost.getTitle())
                        .userId(findUser.getId())
                        .userDeposit(findUser.getDeposit())
                        .build();
        model.addAttribute("paymentDto", paymentDto);
        model.addAttribute("errorMessage", null);

        return "payment/member/payment/paymentForm2";
    }

    @PostMapping("payment/submit")
    public String paymentSubmit(@ModelAttribute("paymentDto") PaymentDto paymentDto, BindingResult result,  Model model, HttpSession session) {
        if(paymentDto.getUserDeposit() - paymentDto.getPostDeposit() < 0){
            model.addAttribute("errorMessage", "잔액이 부족합니다. 보증금을 충전해주세요.");
            return "payment/member/payment/paymentForm2";
        }

        Long loginId = (Long) session.getAttribute("loginId");
        System.out.println("paymentDto = " + paymentDto);

        int payment = paymentService.payment(paymentDto.getPostId(), loginId);
        model.addAttribute("payment", payment);
        return "redirect:/payment/success";
    }
    @PostMapping("payment/submitPost")
    public String paymentSubmitPost(@ModelAttribute("payPostDto")  PayPostDto payPostDto, BindingResult result,  Model model, HttpSession session) {
        if(payPostDto.getUserDeposit() - payPostDto.getDeposit() < 0){
            model.addAttribute("errorMessage", "잔액이 부족합니다. 보증금을 충전해주세요.");
            return "payment/member/payment/paymentPostForm";
        }

        Long loginId = (Long) session.getAttribute("loginId");
        System.out.println("paymentDto = " + payPostDto);

        PaymentApplyUserDeposit applyResult = paymentService.paymentPost(payPostDto);
        model.addAttribute("payment", applyResult.getUserDeposit());
        Long user_id = (Long)session.getAttribute("loginId");
        CreatePostDto createPostDto = CreatePostDto.builder()
                .post_id(payPostDto.getPost_id())
                .count(payPostDto.getCount())
                .startdate(payPostDto.getStartdate())
                .enddate(payPostDto.getEnddate())
                .date(payPostDto.getDate())
                .recruitdate(payPostDto.getRecruitdate())
                .deposit(payPostDto.getDeposit())
                .typeContent(payPostDto.getTypeContent())
                .languageContent(payPostDto.getLanguageContent())
                .title(payPostDto.getTitle())
                .content(payPostDto.getContent())
                .user_id(loginId)
                .subject(payPostDto.getSubject())
                .complete("0")
                .build();
        System.out.println("createPostDto.getPost_id() = " + createPostDto.getPost_id());

        Long postId = postService.createProjectWithPay(createPostDto);
        paymentService.updateApplyPostId(applyResult.getApplyId(), postId);

        model.addAttribute("post", createPostDto);


        return "redirect:/payment/successPost";
    }



    @GetMapping("payment/success")
    public String paymentResult(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("loginId");
        User user = userService.findUser(userId);
        model.addAttribute("payment", user.getDeposit());

        return "payment/member/payment/paymentResult";

    }
    @GetMapping("payment/successPost")
    public String paymentPostResult(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("loginId");
        User user = userService.findUser(userId);
        model.addAttribute("payment", user.getDeposit());

        return "payment/member/payment/paymentPostResult";

    }
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "payment/member/payment/paymentForm2";
    }
}
