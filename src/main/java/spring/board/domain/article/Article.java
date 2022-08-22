package spring.board.domain.article;

import lombok.*;
import spring.board.domain.AuditingFields;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.member.UserAccount;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 10000, nullable = false)
    private String content;

    private String hashtag;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<ArticleComment> articleComments = new HashSet<>();

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.addArticle(this);
    }

    @Builder
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    public void changeArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void addArticleComment(ArticleComment articleComment) {
        articleComments.add(articleComment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
