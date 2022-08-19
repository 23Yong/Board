package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.board.controller.form.LoginForm;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/login")
    public String createLoginForm(@ModelAttribute("loginForm")LoginForm loginForm) {
        return "loginForm";
    }
}
