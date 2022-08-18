package spring.board.domain.articlecomment;

import lombok.*;
import spring.board.domain.AuditingFields;
import spring.board.domain.article.Article;
import spring.board.domain.member.Member;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_comment_id")
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private ArticleComment(String content, Article article) {
        this.content = content;
        this.article = article;
    }

    public static ArticleComment of(String content, Article article) {
        return new ArticleComment(content, article);
    }

    @Builder
    public ArticleComment(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void addWriter(Member writer) {
        this.writer = writer;
    }
    public void addArticle(Article article) {
        article.addArticleComment(this);
        this.article = article;
    }
    public void editReply(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}