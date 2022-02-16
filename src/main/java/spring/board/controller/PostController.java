package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.board.controller.form.PostForm;
import spring.board.domain.Post;
import spring.board.service.PostService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/new")
    public String createPostForm(@ModelAttribute(name = "postForm") PostForm postForm) {
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String createPost(@Validated PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "posts/createPostForm";
        }

        Post post = Post.builder()
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .createdTime(LocalDateTime.now())
                .build();

        postService.registerPost(post);

        return "redirect:/";
    }
}
