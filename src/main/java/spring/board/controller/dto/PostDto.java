package spring.board.controller.dto;

import lombok.*;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;
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

        public Post toEntity(Member member) {
            Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .build();
            post.setMember(member);
            return post;
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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        public PostInfo(Post entity) {
            this.postId = entity.getId();
            this.title = entity.getTitle();
            this.createdTime = entity.getCreatedTime();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
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