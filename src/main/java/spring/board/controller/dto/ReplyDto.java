package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;
import spring.board.domain.reply.Reply;

import java.time.LocalDateTime;
public class ReplyDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReplySaveRequest {

        private String content;

        @Builder
        public ReplySaveRequest(String content) {
            this.content = content;
        }

        public Reply toEntity(Post post, Member member) {
            Reply reply = Reply.builder()
                    .content(content)
                    .build();

            reply.addPost(post);
            reply.addWriter(member);
            return reply;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReplyUpdateRequest {

        private Long id;
        private String content;

        @Builder
        public ReplyUpdateRequest(Long id, String content) {
            this.id = id;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReplyDeleteRequest {

        private Long id;

        @Builder
        public ReplyDeleteRequest(Long id) {
            this.id = id;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReplyInfo {

        private Long id;
        private String content;
        private String nickname;
        private LocalDateTime createdTime;

        @Builder
        public ReplyInfo(Long id, String content, String nickname, LocalDateTime createdTime) {
            this.id = id;
            this.content = content;
            this.nickname = nickname;
            this.createdTime = createdTime;
        }
    }
}