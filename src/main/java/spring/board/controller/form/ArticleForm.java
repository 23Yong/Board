package spring.board.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ArticleForm {

    @NotEmpty(message = "게시글의 제목은 필수로 입력해야 합니다.")
    private String title;
    @NotEmpty(message = "게시글의 내용은 필수로 입력해야 합니다.")
    private String content;
}
