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
import spring.board.domain.article.Article;
import spring.board.service.ArticleService;
import spring.board.service.ArticleCommentService;

import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.PostDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostController {

    private final ArticleService articleService;
    private final ArticleCommentService articleCommentService;

    @GetMapping("/new")
    public String createPostForm(@ModelAttribute(name = "postForm") PostForm postForm) {
        return "posts/createPostForm";
    }

    @GetMapping("/{id}")
    public String getPostInfo(@PathVariable Long id, @LoginCheck Member member,
                              Model model, Pageable pageable) {
        Article findArticle = articleService.findPost(id);
        Member findPostMember = findArticle.getMember();

        PostMemberInfo memberInfo = PostMemberInfo.builder()
                .nickname(findPostMember.getNickname())
                .build();

        PostDetailInfo postDetailInfo = PostDetailInfo.builder()
                .postId(id)
                .title(findArticle.getTitle())
                .content(findArticle.getContent())
                .postMemberInfo(memberInfo)
                .build();

        model.addAttribute("postInfo", postDetailInfo);
        model.addAttribute("loginMember", member);
        model.addAttribute("replies", articleCommentService.findAllReplies(pageable, id));
        return "posts/postInfo";
    }

    @GetMapping("/{postId}/edit")
    public String createPostEditForm(@ModelAttribute(name = "postForm") PostEditForm postEditForm) {
        return "posts/createPostEditForm";
    }
}