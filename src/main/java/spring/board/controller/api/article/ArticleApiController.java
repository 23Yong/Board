package spring.board.controller.api.article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.domain.member.UserAccount;
import spring.board.service.ArticleService;
import spring.board.service.ArticleCommentService;
import static spring.board.controller.dto.ArticleDto.*;
import static spring.board.controller.dto.ArticleCommentDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleApiController {

    private final ArticleService articleService;

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public Long save(@RequestBody ArticleSaveRequest requestDto,
                     @LoginCheck UserAccount userAccount) {

        return articleService.save(requestDto, userAccount.getNickname());
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody ArticleUpdateRequest requestDto) {
        return articleService.update(requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return articleService.delete(id);
    }

    @PostMapping("/{id}/articleComment")
    public Long writeArticleComment(@RequestBody ArticleCommentSaveRequest request, @PathVariable Long id, @LoginCheck UserAccount userAccount) {
        return articleCommentService.save(request, id, userAccount);
    }

    @PutMapping("/{id}/articleComment")
    public Long updateArticleComment(@RequestBody ArticleCommentUpdateRequest request) {
        return articleCommentService.update(request);
    }

    @DeleteMapping("/{id}/articleComment")
    public Long deleteArticleComment(@RequestBody ArticleCommentDeleteRequest request) {
        return articleCommentService.delete(request);
    }
}