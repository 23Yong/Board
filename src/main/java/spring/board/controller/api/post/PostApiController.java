package spring.board.controller.api.post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.domain.member.Member;
import spring.board.service.ArticleService;
import spring.board.service.ArticleCommentService;
import static spring.board.controller.dto.PostDto.*;
import static spring.board.controller.dto.ReplyDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostApiController {

    private final ArticleService articleService;

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public Long save(@RequestBody PostSaveRequest requestDto,
                     @LoginCheck Member member) {

        return articleService.save(requestDto, member.getNickname());
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody PostUpdateRequest requestDto) {
        return articleService.update(requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return articleService.delete(id);
    }

    @PostMapping("/{id}/reply")
    public Long writeReply(@RequestBody ReplySaveRequest request, @PathVariable Long id, @LoginCheck Member member) {
        return articleCommentService.save(request, id, member);
    }

    @PutMapping("/{id}/reply")
    public Long updateReply(@RequestBody ReplyUpdateRequest request) {
        return articleCommentService.update(request);
    }

    @DeleteMapping("/{id}/reply")
    public Long deleteReply(@RequestBody ReplyDeleteRequest request) {
        return articleCommentService.delete(request);
    }
}