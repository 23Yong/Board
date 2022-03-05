package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.board.common.annotation.LoginCheck;
import spring.board.controller.dto.MemberDto;
import spring.board.controller.dto.PostDto;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@LoginCheck Member loginMember, Model model, Pageable pageable) {
        Page<Post> posts = postService.findAllPosts(pageable);
        List<PostDto.PostInfo> postInfoList = posts.getContent()
                .stream()
                .map(post -> PostDto.PostInfo.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .createdDate(post.getCreatedTime())
                        .build())
                .collect(Collectors.toList());

        model.addAttribute("posts", postInfoList);
        model.addAttribute("postPage", posts);
        if (loginMember == null) {
            return "home";
        }

        MemberDto.LoginMember member = MemberDto.LoginMember.builder()
                .loginId(loginMember.getLoginId())
                .nickname(loginMember.getNickname())
                .build();

        model.addAttribute("member", member);
        return "home";
    }
}
