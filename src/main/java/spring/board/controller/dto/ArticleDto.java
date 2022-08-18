package spring.board.controller.dto;
import lombok.*;
import spring.board.domain.article.Article;
import spring.board.domain.member.Member;

import java.time.LocalDateTime;
import static spring.board.controller.dto.MemberDto.*;
public class ArticleDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ArticleSaveRequest {
        private String title;
        private String content;
        @Builder
        public ArticleSaveRequest(String title, String content) {
            this.title = title;
            this.content = content;
        }
        public Article toEntity(Member member) {
            Article article = Article.builder()
                    .title(title)
                    .content(content)
                    .build();
            article.setMember(member);
            return article;
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ArticleUpdateRequest {
        private Long id;
        private String title;
        private String content;
        @Builder
        public ArticleUpdateRequest(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ArticleInfo {
        private Long articleId;
        private String title;
        private LocalDateTime createdTime;
        @Builder
        public ArticleInfo(Long articleId, String title, LocalDateTime createdTime) {
            this.articleId = articleId;
            this.title = title;
            this.createdTime = createdTime;
        }
        public ArticleInfo(Article entity) {
            this.articleId = entity.getId();
            this.title = entity.getTitle();
            this.createdTime = entity.getCreatedAt();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ArticleDetailInfo {

        private Long articleId;
        private String title;
        private String content;
        private ArticleMemberInfo articleMemberInfo;

        @Builder
        public ArticleDetailInfo(Long articleId, String title, String content, ArticleMemberInfo articleMemberInfo) {
            this.articleId = articleId;
            this.title = title;
            this.content = content;
            this.articleMemberInfo = articleMemberInfo;
        }
    }
}