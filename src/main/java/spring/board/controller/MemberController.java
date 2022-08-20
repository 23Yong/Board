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
                .userId(memberForm.getUserId())
                .userPassword(memberForm.getUserPassword())
                .nickname(memberForm.getNickname())
                .build();

        memberService.save(saveRequest);
        return "redirect:/";
    }

    @GetMapping("members/{userId}/myPage")
    public String myPage(@PathVariable String userId, Model model) {
        UserAccount findUserAccount = memberService.findByUserId(userId);
        MemberInfo member = MemberInfo.builder()
                .userId(findUserAccount.getUserId())
                .nickname(findUserAccount.getNickname())
                .build();
        model.addAttribute("member", member);

        return "members/my-page/myPage";
    }

    @GetMapping("members/{userId}/edit")
    public String createEditForm(@PathVariable String userId,
            @ModelAttribute(name = "member") MemberEditForm memberEditForm) {
        memberEditForm.setUserId(userId);
        return "members/createEditForm";
    }

    @GetMapping("members/{userId}/edit/password")
    public String createPasswordEditForm(@PathVariable String userId,
            @ModelAttribute(name = "passwordForm") PasswordEditForm passwordEditForm) {
        passwordEditForm.setUserId(userId);
        return "members/createPasswordEditForm";
    }
}
