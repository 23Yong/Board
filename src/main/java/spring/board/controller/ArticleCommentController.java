package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.board.dto.UserAccountDto;
import spring.board.dto.request.ArticleCommentRequest;
import spring.board.service.ArticleCommentService;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        // TODO: 인증정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(
                UserAccountDto.of("23Yong", "23Yong@email.com", "password", "23Yong", "memo")
        ));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{articleCommentId}/delete")
    public String deleteArticleComment(@PathVariable Long articleCommentId, Long articleId) {
        articleCommentService.deleteArticleComment(articleCommentId);

        return "redirect:/articles/" + articleId;
    }
}
