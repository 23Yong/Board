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

    private final LoginService loginService;

    @ExceptionHandler(CredentialException.class)
    private String handleCredentialException(CredentialException ex, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("message", "가입되지 않은 사용자이거나 잘못된 비밀번호 입니다.");
        return "loginForm";
    }

    @GetMapping("/login")
    public String createLoginForm(@ModelAttribute("loginForm")LoginForm loginForm) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "loginForm";
        }

        loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        loginService.logout();

        return "redirect:/";
    }
}
