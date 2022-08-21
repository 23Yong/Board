package spring.board.dto;

import java.time.LocalDateTime;

public record ArticleCommentUpdateDto(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content
) {

    public static ArticleCommentUpdateDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String content) {
        return new ArticleCommentUpdateDto(createdAt, createdBy, modifiedAt, modifiedBy, content);
    }
}
