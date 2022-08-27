package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.board.dto.UserAccountDto;
import spring.board.dto.request.ArticleCommentRequest;
import spring.board.dto.security.BoardPrincipal;
import spring.board.service.ArticleCommentService;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleCommentRequest articleCommentRequest
    ) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{articleCommentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long articleCommentId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            Long articleId
    ) {
        articleCommentService.deleteArticleComment(articleCommentId, boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }
}
