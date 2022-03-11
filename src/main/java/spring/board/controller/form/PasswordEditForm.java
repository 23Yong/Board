package spring.board.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class PasswordEditForm {

    private String loginId;
    @NotEmpty(message = "이전 비밀번호는 필수로 입력해야 합니다.")
    private String prevPassword;
    @NotEmpty(message = "신규 비밀번호는 필수로 입력해야 합니다.")
    private String afterPassword;
}
