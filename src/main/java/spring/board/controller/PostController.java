package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.board.controller.form.PostForm;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.service.PostService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String createPost(PostForm postForm) {
        // 임시 멤버 생성 -> 후에 현재 접속한 회원정보로 대체
        Member member = Member.builder()
                .loginId("tempLoginId")
                .password("tempPassword")
                .nickname("tempNickname")
                .build();

        Post post = Post.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .createdTime(LocalDateTime.now())
                .build();

        postService.registerPost(member.getId(), post);

        return "redirect:/";
    }
}
