package com.example.fighteam.payment.controller;

import com.example.fighteam.payment.domain.History;
import com.example.fighteam.payment.repository.HistoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryRepository historyRepository;
    @GetMapping("/test")
    public String test() {
        return "payment/member/payment/test/test";
    }

    @GetMapping("/history")
    public String getHistory() {


        return "redirect:/historySubmit";
    }

    @PostMapping("/historySubmit")
    public String postHistoryList(@RequestParam("type") String type, Model model, HttpSession session) {

        Long id = (Long) session.getAttribute("loginId");

        List<History> findHistory = historyRepository.findByMemberId(id, type);

        model.addAttribute("historyList", findHistory);
        return "history/historyList";
    }
    @GetMapping("/historySubmit")
    public String getHistoryList( Model model, HttpSession session) {

        Long id = (Long) session.getAttribute("loginId");

        List<History> findHistory = historyRepository.findByMemberId(id, "all");

        model.addAttribute("historyList", findHistory);
        return "history/historyList";
    }
}
