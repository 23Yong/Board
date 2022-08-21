package spring.board.domain.articlecomment;

import lombok.*;
import spring.board.domain.AuditingFields;
import spring.board.domain.article.Article;
import spring.board.domain.member.UserAccount;

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

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private ArticleComment(UserAccount userAccount, String content, Article article) {
        this.userAccount = userAccount;
        this.content = content;
        this.article = article;
    }

    public static ArticleComment of(UserAccount userAccount, String content, Article article) {
        return new ArticleComment(userAccount, content, article);
    }

    @Builder
    public ArticleComment(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void addWriter(UserAccount writer) {
        this.userAccount = writer;
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