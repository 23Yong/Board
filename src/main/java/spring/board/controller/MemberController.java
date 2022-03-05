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
import spring.board.domain.Member;
import spring.board.service.MemberService;

import javax.validation.Valid;

import static spring.board.controller.dto.MemberDto.*;

@Controller
@RequiredArgsConstructor
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

        SaveRequest member = SaveRequest.builder()
                .loginId(memberForm.getLoginId())
                .password(memberForm.getPassword())
                .nickname(memberForm.getNickname())
                .build();

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("members/{loginId}/myPage")
    public String myPage(@PathVariable String loginId, Model model) {
        Member findMember = memberService.findByLoginId(loginId);
        LoginMember member = LoginMember.builder()
                .loginId(findMember.getLoginId())
                .nickname(findMember.getNickname())
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

    @PostMapping("members/{loginId}/edit")
    public String editMemberInfo(@PathVariable String loginId,
                                 @Valid @ModelAttribute(name = "member") MemberEditForm memberEditForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/createEditForm";
        }

        EditMemberInfoRequest member = EditMemberInfoRequest.builder()
                .loginId(memberEditForm.getLoginId())
                .nickname(memberEditForm.getNickname())
                .build();
        memberService.updateNickname(member);

        return "redirect:/members/{loginId}/myPage";
    }

    @GetMapping("members/{loginId}/edit/password")
    public String createPasswordEditForm(@PathVariable String loginId,
            @ModelAttribute(name = "passwordForm") PasswordEditForm passwordEditForm) {
        passwordEditForm.setLoginId(loginId);
        return "members/createPasswordEditForm";
    }

    @PostMapping("members/{loginId}/edit/password")
    public String editPassword(@PathVariable String loginId,
                               @Valid @ModelAttribute(name = "passwordForm") PasswordEditForm passwordEditForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/createPasswordEditForm";
        }

        ChangePasswordRequest passwordRequest = ChangePasswordRequest.builder()
                .loginId(loginId)
                .prevPassword(passwordEditForm.getPrevPassword())
                .afterPassword(passwordEditForm.getAfterPassword())
                .build();
        memberService.updatePassword(loginId, passwordRequest);

        return "redirect:/members/{loginId}/myPage";
    }
}
