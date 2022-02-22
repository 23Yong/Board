package spring.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.board.common.annotation.LoginCheck;
import spring.board.controller.form.PostForm;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.exception.member.UnauthenticatedUserException;
import spring.board.service.PostService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/new")
    public String createPostForm(@ModelAttribute(name = "postForm") PostForm postForm) {
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String createPost(@Validated PostForm postForm, BindingResult bindingResult, @LoginCheck Member member) {
        if (bindingResult.hasErrors()) {
            return "posts/createPostForm";
        }

        Post post = Post.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .createdTime(LocalDateTime.now())
                .build();

        log.info("member = {}, {}", member.getId(), member.getLoginId());

        if (member == null) {
            throw new UnauthenticatedUserException("게시판 작성은 로그인 후 이용해주세요");
        }

        postService.registerPost(member.getLoginId(), post);

        return "redirect:/";
    }
}
