package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.article.Article;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.member.Member;

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

        public ArticleComment toEntity(Article article, Member member) {
            ArticleComment articleComment = ArticleComment.builder()
                    .content(content)
                    .build();

            articleComment.addPost(article);
            articleComment.addWriter(member);
            return articleComment;
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