package com.example.fighteam.user.controller;

import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.domain.repository.UserRepository;
import com.example.fighteam.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    public final UserService userService;


    @GetMapping("/user/login")
    public String mainForm() {
        return "joinlogin/login";
    }

    @GetMapping("/user/join")
    public String createUserForm() {
        return "joinlogin/join";
    }

    @GetMapping("/user/edit")
    public String editUserForm(Model model, HttpSession session) {
        model.addAttribute("userScore", userRepository.findById((Long) session.getAttribute("loginId")).get().getScore());
        return "joinlogin/userEdit";
    }


    //회원가입
    @RequestMapping(value = "/user/join", method = RequestMethod.POST)
    public String joinUs(User user) {
        User dup = userRepository.findById(user.getId()).orElse(null);
        if(dup != null) return "redirect:/user/join";
        userService.signUp(user);
        return "joinlogin/login";
    }

    //로그인
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(User member, Model model, HttpSession session) {
        User user = userService.loginUser(member.getEmail(), member.getPasswd());

        // 아이디 비밀번호 불일치
        if (user == null) {
            model.addAttribute("loginMessage", "아이디 혹은 비밀번호가 일치하지 않습니다.");
            return "joinlogin/login";
        }

        session.setAttribute("loginId", user.getId());        //세션에 아이디를 저장함.
        //로그인한 유저가 누군지 확인가능

        model.addAttribute("name", user.getName());
        return "redirect:/post/home";
    }


    //회원정보수정
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editUser(@RequestParam("passwd") String password,@RequestParam("tel") String userTel,HttpSession session,Model model) {
        User user = userRepository.findById((Long) session.getAttribute("loginId")).get();
        user.setPasswd(password);
        user.setTel(userTel);
        userRepository.save(user);

        return "redirect:/user/edit";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/post/home";
    }


}



