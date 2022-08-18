package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.article.Article;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.member.Member;

import java.time.LocalDateTime;
public class ArticleCommentDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ArticleCommentSaveRequest {

        private String content;

        @Builder
        public ArticleCommentSaveRequest(String content) {
            this.content = content;
        }

        public ArticleComment toEntity(Article article, Member member) {
            ArticleComment articleComment = ArticleComment.builder()
                    .content(content)
                    .build();

            articleComment.addArticle(article);
            articleComment.addWriter(member);
            return articleComment;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ArticleCommentUpdateRequest {

        private Long id;
        private String content;

        @Builder
        public ArticleCommentUpdateRequest(Long id, String content) {
            this.id = id;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ArticleCommentDeleteRequest {

        private Long id;

        @Builder
        public ArticleCommentDeleteRequest(Long id) {
            this.id = id;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ArticleCommentInfo {

        private Long id;
        private String content;
        private String nickname;
        private LocalDateTime createdTime;

        @Builder
        public ArticleCommentInfo(Long id, String content, String nickname, LocalDateTime createdTime) {
            this.id = id;
            this.content = content;
            this.nickname = nickname;
            this.createdTime = createdTime;
        }
    }
}