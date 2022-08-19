package spring.board.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.controller.form.ArticleEditForm;
import spring.board.controller.form.ArticleForm;
import spring.board.domain.member.Member;
import spring.board.domain.article.Article;
import spring.board.service.ArticleService;
import spring.board.service.ArticleCommentService;

import java.util.List;

import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.ArticleDto.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleCommentService articleCommentService;

    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());
        return "articles/index";
    }

    @GetMapping("/new")
    public String createArticleForm(@ModelAttribute(name = "articleForm") ArticleForm articleForm) {
        return "/articles/createArticleForm";
    }

    @GetMapping("/{id}")
    public String getArticleInfo(@PathVariable Long id, @LoginCheck Member member,
                                 Model model, Pageable pageable) {
        Article findArticle = articleService.findArticle(id);
        Member findArticleMember = findArticle.getMember();

        ArticleMemberInfo memberInfo = ArticleMemberInfo.builder()
                .nickname(findArticleMember.getNickname())
                .build();

        ArticleDetailInfo articleDetailInfo = ArticleDetailInfo.builder()
                .articleId(id)
                .title(findArticle.getTitle())
                .content(findArticle.getContent())
                .articleMemberInfo(memberInfo)
                .build();

        model.addAttribute("articleInfo", articleDetailInfo);
        model.addAttribute("loginMember", member);
        model.addAttribute("replies", articleCommentService.findAllReplies(pageable, id));
        return "/articles/ArticleInfo";
    }

    @GetMapping("/{articleId}/edit")
    public String createArticleEditForm(@ModelAttribute(name = "articleForm") ArticleEditForm articleEditForm) {
        return "/articles/createArticleEditForm";
    }
}