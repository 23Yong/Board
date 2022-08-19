package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring.board.controller.form.MemberEditForm;
import spring.board.controller.form.MemberForm;
import spring.board.controller.form.PasswordEditForm;
import spring.board.domain.member.UserAccount;
import spring.board.service.MemberService;

import static spring.board.controller.dto.MemberDto.*;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createMemberForm(@ModelAttribute(name = "memberForm") MemberForm memberForm) {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Validated MemberForm memberForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/members/createMemberForm";
        }

        MemberSaveRequest saveRequest = MemberSaveRequest.builder()
                .email(memberForm.getEmail())
                .loginId(memberForm.getLoginId())
                .password(memberForm.getPassword())
                .nickname(memberForm.getNickname())
                .build();

        memberService.save(saveRequest);
        return "redirect:/";
    }

    @GetMapping("members/{loginId}/myPage")
    public String myPage(@PathVariable String loginId, Model model) {
        UserAccount findUserAccount = memberService.findByLoginId(loginId);
        MemberInfo member = MemberInfo.builder()
                .loginId(findUserAccount.getUserId())
                .nickname(findUserAccount.getNickname())
                .build();
        model.addAttribute("member", member);

        return "members/my-page/myPage";
    }

    @GetMapping("members/{loginId}/edit")
    public String createEditForm(@PathVariable String loginId,
            @ModelAttribute(name = "member") MemberEditForm memberEditForm) {
        memberEditForm.setLoginId(loginId);
        return "members/createEditForm";
    }

    @GetMapping("members/{loginId}/edit/password")
    public String createPasswordEditForm(@PathVariable String loginId,
            @ModelAttribute(name = "passwordForm") PasswordEditForm passwordEditForm) {
        passwordEditForm.setLoginId(loginId);
        return "members/createPasswordEditForm";
    }
}
