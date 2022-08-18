package spring.board.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ArticleEditForm {

    private Long articleId;
    @NotEmpty(message = "게시글의 제목은 반드시 있어야 합니다.")
    private String title;
    @NotEmpty(message = "게시글의 내용은 반드시 있어야 합니다.")
    private String content;
}
