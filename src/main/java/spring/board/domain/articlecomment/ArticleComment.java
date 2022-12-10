package spring.board.domain.articlecomment;

import lombok.*;
import spring.board.domain.AuditingFields;
import spring.board.domain.article.Article;
import spring.board.domain.member.UserAccount;

import javax.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    @Setter
    @Column(updatable = false)
    private Long parentCommentId;

    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private Set<ArticleComment> childComments  = new LinkedHashSet<>();

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = LAZY, optional = false)
    private UserAccount userAccount;

    @ManyToOne(fetch = LAZY, optional = false)
    private Article article;

    public void changeContent(String content) {
        this.content = content;
    }

    public void addChildComment(ArticleComment child) {
        child.setParentCommentId(this.getId());
        this.getChildComments().add(child);
    }

    private ArticleComment(UserAccount userAccount, String content, Long parentCommentId, Article article) {
        this.userAccount = userAccount;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.article = article;
    }

    public static ArticleComment of(UserAccount userAccount, String content, Article article) {
        return new ArticleComment(userAccount, content, null, article);
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