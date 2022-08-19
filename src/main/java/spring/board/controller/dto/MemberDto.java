package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.Role;

public class MemberDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MemberInfo {

        private String userId;
        private String nickname;

        @Builder
        public MemberInfo(String userId, String nickname) {
            this.userId = userId;
            this.nickname = nickname;
        }

        public UserAccount toEntity() {
            return UserAccount.builder()
                    .userId(userId)
                    .nickname(nickname)
                    .build();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MemberSaveRequest {

        private String email;
        private String userId;
        private String userPassword;
        private String nickname;
        private Role role;

        @Builder
        public MemberSaveRequest(String email, String userId, String userPassword, String nickname) {
            this.email = email;
            this.userId = userId;
            this.userPassword = userPassword;
            this.nickname = nickname;
            this.role = Role.USER;
        }

        public UserAccount toEntity() {
            return UserAccount.builder()
                    .email(email)
                    .userId(userId)
                    .userPassword(userPassword)
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
    public static class ArticleMemberInfo {

        private String nickname;

        @Builder
        public ArticleMemberInfo(String nickname) {
            this.nickname = nickname;
        }
    }
}
