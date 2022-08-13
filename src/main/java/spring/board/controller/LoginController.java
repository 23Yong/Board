package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.board.common.SessionConst;
import spring.board.controller.form.LoginForm;
import spring.board.domain.member.Member;
import spring.board.exception.member.CredentialException;
import spring.board.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/login")
    public String createLoginForm(@ModelAttribute("loginForm")LoginForm loginForm) {
        return "loginForm";
    }
}
