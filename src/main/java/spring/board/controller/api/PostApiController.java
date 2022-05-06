package spring.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.controller.dto.PostDto;
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

    @PostMapping("/{postId}/reply")
    public Long writeReply(@RequestBody ReplySaveRequest request) {
        return replyService.registerReply(request);
    }
}
