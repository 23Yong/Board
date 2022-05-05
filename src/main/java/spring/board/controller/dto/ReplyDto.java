package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.Reply;

import java.time.LocalDateTime;

public class ReplyDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReplyRequest {

        private Long id;

        private String content;
        private Long memberId;
        private Long postId;

        @Builder
        public ReplyRequest(Long id, String content, Long memberId, Long postId) {
            this.id = id;
            this.content = content;
            this.memberId = memberId;
            this.postId = postId;
        }

        public Reply toEntity() {
            return Reply.builder()
                    .id(this.id)
                    .content(this.content)
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReplySaveRequest {

        private String nickname;
        private String content;
        private Long postId;

        @Builder
        public ReplySaveRequest(String nickname, String content, Long postId) {
            this.nickname = nickname;
            this.content = content;
            this.postId = postId;
        }

        public Reply toEntity(String content) {
            return Reply.builder()
                    .content(content)
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReplyResponse {

        private Long id;
        private String content;
        private LocalDateTime updatedTime;

        @Builder
        public ReplyResponse(Long id, String content, LocalDateTime updatedTime) {
            this.id = id;
            this.content = content;
            this.updatedTime = updatedTime;
        }

        public ReplyResponse(Reply entity) {
            this.id = entity.getId();
            this.content = entity.getContent();
            this.updatedTime = entity.getUpdatedTime();
        }
    }
}
