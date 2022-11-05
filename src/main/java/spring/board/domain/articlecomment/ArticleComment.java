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
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = LAZY, optional = false)
    private UserAccount userAccount;

    @ManyToOne(fetch = LAZY, optional = false)
    private Article article;

    public void changeContent(String content) {
        this.content = content;
    }

    private ArticleComment(UserAccount userAccount, String content, Article article) {
        this.userAccount = userAccount;
        this.content = content;
        this.article = article;
    }

    public static ArticleComment of(UserAccount userAccount, String content, Article article) {
        return new ArticleComment(userAccount, content, article);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return this.getId() != null && this.getId().equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}