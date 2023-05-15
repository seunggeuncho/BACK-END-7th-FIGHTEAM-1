package com.example.fighteam.payment.controller;

import com.example.fighteam.payment.service.ChargeService;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import com.example.fighteam.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;
    private final UserService userService;
    @GetMapping("/login/pay")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "payment/member/login";
    }
//    @PostMapping("/login/pay")
//    public String Login(LoginDto loginDto, HttpSession session) {
//        Long loginId = chargeService.login(loginDto.email, loginDto.password);
//        session.setAttribute("loginId", loginId);
//
//        return "payment/member/main";
//    }

    @GetMapping("/main")
    public String main() {
        return "hwang/member/main";
    }

    public String chargeForm(Model model, HttpSession session) {

        Long loginId = (Long) session.getAttribute("loginId");
        User user = userService.findUser(loginId);


//        System.out.println("findmember.getDeposit() = " + findmember.getDeposit());
        model.addAttribute("balance", user.getDeposit());
        return "payment/member/charge/chargeForm2";
    }

    @RequestMapping("/charge")
    public String chargeFormJson(Model model, HttpSession session) {

        Long loginId = (Long) session.getAttribute("loginId");
        System.out.println("loginId = " + loginId);
//        Member findmember = memberRepository.findMember(loginId);
        User user = userService.findUser(loginId);
//        System.out.println("findmember.getDeposit() = " + findmember.getDeposit());
        model.addAttribute("balance", user.getDeposit());
        return "payment/member/charge/chargeForm2";
    }

    @PostMapping("/charge/submit")
    public String chargeResult(@RequestParam("charge-amount") int cost, HttpSession session, Model model) {
        Long loginId = (Long) session.getAttribute("loginId");
//        System.out.println("loginId = " + loginId);
        int deposit = chargeService.chargeDeposit(loginId, cost);

        return "redirect:/chargeSuccess";
    }

    @GetMapping("/chargeSuccess")
    public String chargeSuceess(HttpSession session, Model model) {
        Long loginId = (Long) session.getAttribute("loginId");
//        Member findMember = memberRepository.findMember(loginId);
        User user = userService.findUser(loginId);
        model.addAttribute("balance", user.getDeposit());

        return "payment/member/charge/chargeResult";
    }
    @Data
    static class LoginDto {

        private String email;
        private String password;

    }
}

