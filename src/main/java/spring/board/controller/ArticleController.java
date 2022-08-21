package spring.board.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import spring.board.service.ArticleService;
import spring.board.service.ArticleCommentService;

import java.util.List;

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

    @GetMapping("/{articleId}")
    public String getArticleInfo(@PathVariable Long articleId, ModelMap map) {
        map.addAttribute("article", "article"); // TODO: 실제 데이터를 넣어줘야 한다.
        map.addAttribute("articleComments", List.of());
        return "/articles/detail";
    }
}