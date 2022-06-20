package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.board.common.annotation.LoginCheck;
import spring.board.domain.member.Member;
import spring.board.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.PostDto.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@LoginCheck Member loginMember, Model model, Pageable pageable) {
        Page<PostInfo> posts = postService.findAllPosts(pageable);
        List<PostInfo> postInfoList = posts.stream().collect(Collectors.toList());

        model.addAttribute("posts", postInfoList);
        model.addAttribute("postPage", posts);

        if (loginMember == null) {
            return "home";
        }

        MemberInfo member = MemberInfo.builder()
                .loginId(loginMember.getLoginId())
                .nickname(loginMember.getNickname())
                .build();

        model.addAttribute("member", member);
        return "home";
    }
}
