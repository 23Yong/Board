package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.board.controller.form.MemberForm;
import spring.board.controller.form.PostForm;
import spring.board.domain.Member;
import spring.board.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createMemberForm(Model model) {
        MemberForm memberForm = new MemberForm();
        model.addAttribute("memberForm", memberForm);

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Validated MemberForm memberForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "/members/createMemberForm";
        }

        Member member = Member.builder()
                .loginId(memberForm.getLoginId())
                .password(memberForm.getPassword())
                .nickname(memberForm.getNickname())
                .build();

        memberService.join(member);
        return "redirect:/";
    }
}
