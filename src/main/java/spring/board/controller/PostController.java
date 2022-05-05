package spring.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.controller.dto.MemberDto;
import spring.board.controller.dto.PostDto;
import spring.board.controller.dto.ReplyDto;
import spring.board.controller.form.PostEditForm;
import spring.board.controller.form.PostForm;
import spring.board.controller.form.ReplyForm;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.exception.member.UnauthenticatedUserException;
import spring.board.service.PostService;
import spring.board.service.ReplyService;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.PostDto.*;
import static spring.board.controller.dto.ReplyDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ReplyService replyService;

    @GetMapping("/new")
    public String createPostForm(@ModelAttribute(name = "postForm") PostForm postForm) {
        return "posts/createPostForm";
    }

    @PostMapping("/new")
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

    @GetMapping("/{postId}")
    public String getPostInfo(@PathVariable Long postId, Model model, Pageable pageable) {
        Post findPost = postService.findPost(postId);
        Member findPostMember = findPost.getMember();

        PostMemberInfo memberInfo = PostMemberInfo.builder()
                .nickname(findPostMember.getNickname())
                .build();

        PostDetailRequest postDetailRequest = PostDetailRequest.builder()
                .postId(postId)
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .postMemberInfo(memberInfo)
                .build();

        model.addAttribute("postInfo", postDetailRequest);
        model.addAttribute("replies", replyService.findAllReplies(pageable));
        return "posts/postInfo";
    }

    @GetMapping("/{postId}/edit")
    public String createPostEditForm(@ModelAttribute(name = "postForm") PostEditForm postEditForm) {
        return "posts/createPostEditForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPostInfo(@PathVariable Long postId,
                               @Valid @ModelAttribute(name = "postForm") PostEditForm postEditForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "posts/createPostEditForm";
        }

        PostEditRequest postEditRequest = PostEditRequest.builder()
                    .postId(postId)
                    .title(postEditForm.getTitle())
                    .content(postEditForm.getContent())
                    .build();

        postService.updatePost(postEditRequest);

        return "redirect:/";
    }
}
