package spring.board.controller.api.post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.domain.member.Member;
import spring.board.service.PostService;
import spring.board.service.ReplyService;
import static spring.board.controller.dto.PostDto.*;
import static spring.board.controller.dto.ReplyDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostApiController {

    private final PostService postService;

    private final ReplyService replyService;

    @PostMapping("/new")
    public Long save(@RequestBody PostSaveRequest requestDto,
                     @LoginCheck Member member) {

        return postService.save(requestDto, member.getNickname());
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody PostUpdateRequest requestDto) {
        return postService.update(requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return postService.delete(id);
    }

    @PostMapping("/{id}/reply")
    public Long writeReply(@RequestBody ReplySaveRequest request, @PathVariable Long id, @LoginCheck Member member) {
        return replyService.save(request, id, member);
    }

    @PutMapping("/{id}/reply")
    public Long updateReply(@RequestBody ReplyUpdateRequest request) {
        return replyService.update(request);
    }

    @DeleteMapping("/{id}/reply")
    public Long deleteReply(@RequestBody ReplyDeleteRequest request) {
        return replyService.delete(request);
    }
}