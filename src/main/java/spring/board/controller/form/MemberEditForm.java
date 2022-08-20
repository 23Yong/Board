package spring.board.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberEditForm {

    private String userId;
    @NotEmpty(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickname;
}
