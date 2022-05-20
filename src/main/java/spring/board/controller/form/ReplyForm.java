package spring.board.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ReplyForm {

    @NotEmpty(message = "댓글의 내용은 필수로 입력해야 합니다.")
    private String content;
}
