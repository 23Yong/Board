package spring.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.board.common.annotation.LoginCheck;
import spring.board.controller.dto.ReplyDto;
import spring.board.controller.form.ReplyForm;
import spring.board.domain.Member;
import spring.board.service.PostService;
import spring.board.service.ReplyService;

import javax.validation.Valid;

import static spring.board.controller.dto.ReplyDto.*;


@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostApiController {

    private final ReplyService replyService;

    @PostMapping("/{postId}/reply")
    public Long writeReply(@RequestBody ReplySaveRequest request) {
        return replyService.registerReply(request);
    }
}
