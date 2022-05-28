package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.member.Member;
import spring.board.domain.member.Role;

public class MemberDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MemberInfo {

        private String loginId;
        private String nickname;

        @Builder
        public MemberInfo(String loginId, String nickname) {
            this.loginId = loginId;
            this.nickname = nickname;
        }

        public Member toEntity() {
            return Member.builder()
                    .loginId(loginId)
                    .nickname(nickname)
                    .build();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MemberSaveRequest {

        private String email;
        private String loginId;
        private String password;
        private String nickname;
        private Role role;

        public void encodingPassword(String password) {
            this.password = password;
        }

        @Builder
        public MemberSaveRequest(String email, String loginId, String password, String nickname) {
            this.email = email;
            this.loginId = loginId;
            this.password = password;
            this.nickname = nickname;
            this.role = Role.USER;
        }

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .loginId(loginId)
                    .password(password)
                    .nickname(nickname)
                    .role(role)
                    .build();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PasswordUpdateRequest {
        private String prevPassword;
        private String afterPassword;

        @Builder
        public PasswordUpdateRequest(String prevPassword, String afterPassword) {
            this.prevPassword = prevPassword;
            this.afterPassword = afterPassword;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MemberUpdateRequest {

        private String nickname;

        @Builder
        public MemberUpdateRequest(String nickname) {
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
