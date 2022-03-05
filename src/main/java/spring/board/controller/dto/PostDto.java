package spring.board.controller.dto;

import lombok.*;

import java.time.LocalDateTime;

public class PostDto {

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Data
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
}
