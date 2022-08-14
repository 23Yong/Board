package spring.board.controller.dto;
import lombok.*;
import spring.board.domain.article.Article;
import spring.board.domain.member.Member;

import java.time.LocalDateTime;
import static spring.board.controller.dto.MemberDto.*;
public class PostDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostSaveRequest {
        private String title;
        private String content;
        @Builder
        public PostSaveRequest(String title, String content) {
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
    public static class PostUpdateRequest {
        private Long id;
        private String title;
        private String content;
        @Builder
        public PostUpdateRequest(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PostInfo {
        private Long postId;
        private String title;
        private LocalDateTime createdTime;
        @Builder
        public PostInfo(Long postId, String title, LocalDateTime createdTime) {
            this.postId = postId;
            this.title = title;
            this.createdTime = createdTime;
        }
        public PostInfo(Article entity) {
            this.postId = entity.getId();
            this.title = entity.getTitle();
            this.createdTime = entity.getCreatedAt();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PostDetailInfo {

        private Long postId;
        private String title;
        private String content;
        private PostMemberInfo postMemberInfo;

        @Builder
        public PostDetailInfo(Long postId, String title, String content, PostMemberInfo postMemberInfo) {
            this.postId = postId;
            this.title = title;
            this.content = content;
            this.postMemberInfo = postMemberInfo;
        }
    }
}