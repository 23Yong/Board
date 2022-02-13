package spring.board.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "아이디는 필수로 입력해야 합니다.")
    private String loginId;
    @NotEmpty(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;
    @NotEmpty(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickname;
}
