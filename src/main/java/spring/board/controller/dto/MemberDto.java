package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.Member;

public class MemberDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class LoginMember {

        private String loginId;
        private String nickname;

        @Builder
        public LoginMember (String loginId, String nickname) {
            this.loginId = loginId;
            this.nickname = nickname;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SaveRequest {

        private String loginId;
        private String password;
        private String nickname;

        @Builder
        public SaveRequest(String loginId, String password, String nickname) {
            this.loginId = loginId;
            this.password = password;
            this.nickname = nickname;
        }

        public Member toEntity() {
            return Member.builder()
                    .loginId(this.loginId)
                    .password(this.password)
                    .nickname(this.nickname)
                    .build();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ChangePasswordRequest {

        private String loginId;

        private String prevPassword;
        private String afterPassword;

        @Builder
        public ChangePasswordRequest(String loginId,
                                     String prevPassword, String afterPassword) {
            this.loginId = loginId;
            this.prevPassword = prevPassword;
            this.afterPassword = afterPassword;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class EditMemberInfoRequest {

        private String loginId;
        private String nickname;

        @Builder
        public EditMemberInfoRequest(String loginId, String nickname) {
            this.loginId = loginId;
            this.nickname = nickname;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PostMemberInfo {

        private String nickname;

        @Builder
        public PostMemberInfo(String nickname) {
            this.nickname = nickname;
        }
    }
}
