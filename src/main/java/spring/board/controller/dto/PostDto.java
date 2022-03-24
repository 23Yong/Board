package spring.board.controller.dto;

import lombok.*;

import java.time.LocalDateTime;

import static spring.board.controller.dto.MemberDto.*;

public class PostDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PostInfo {

        private Long postId;
        private String title;
        private LocalDateTime createDate;

        @Builder
        public PostInfo(Long postId, String title, LocalDateTime createdDate) {
            this.postId = postId;
            this.title = title;
            this.createDate = createdDate;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PostDetailRequest {

        private Long postId;
        private String title;
        private String content;
        private PostMemberInfo postMemberInfo;

        @Builder
        public PostDetailRequest(Long postId, String title, String content, PostMemberInfo postMemberInfo) {
            this.postId = postId;
            this.title = title;
            this.content = content;
            this.postMemberInfo = postMemberInfo;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PostEditRequest {

        private Long postId;
        private String title;
        private String content;

        @Builder
        public PostEditRequest(Long postId, String title, String content) {
            this.postId = postId;
            this.title = title;
            this.content = content;
        }
    }
}
