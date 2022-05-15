package spring.board.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.board.controller.form.PostEditForm;
import spring.board.controller.form.PostForm;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;
import spring.board.service.PostService;
import spring.board.service.ReplyService;

import javax.validation.Valid;

import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.PostDto.*;

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
}