package spring.board.controller.dto;

import lombok.Builder;

public class MemberDto {

    public static class LoginMember {

        private String loginId;
        private String nickname;

        @Builder
        public LoginMember (String loginId, String nickname) {
            this.loginId = loginId;
            this.nickname = nickname;
        }
    }
}
