package spring.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.controller.form.PostEditForm;
import spring.board.controller.form.PostForm;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;
import spring.board.service.PostService;
import spring.board.service.ReplyService;

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

    @GetMapping("/{id}")
    public String getPostInfo(@PathVariable Long id, @LoginCheck Member member,
                              Model model, Pageable pageable) {
        Post findPost = postService.findPost(id);
        Member findPostMember = findPost.getMember();

        PostMemberInfo memberInfo = PostMemberInfo.builder()
                .nickname(findPostMember.getNickname())
                .build();

        PostDetailInfo postDetailInfo = PostDetailInfo.builder()
                .postId(id)
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .postMemberInfo(memberInfo)
                .build();

        model.addAttribute("postInfo", postDetailInfo);
        model.addAttribute("loginMember", member);
        model.addAttribute("replies", replyService.findAllReplies(pageable, id));
        return "posts/postInfo";
    }

    @GetMapping("/{postId}/edit")
    public String createPostEditForm(@ModelAttribute(name = "postForm") PostEditForm postEditForm) {
        return "posts/createPostEditForm";
    }
}
