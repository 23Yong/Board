package spring.board.domain.article;

import lombok.*;
import spring.board.domain.AuditingFields;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.hashtag.Hashtag;
import spring.board.domain.member.UserAccount;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 10000, nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<ArticleComment> articleComments = new HashSet<>();

    @JoinTable(
            name = "article_hashtag",
            joinColumns = @JoinColumn(name = "articleId"),
            inverseJoinColumns = @JoinColumn(name = "hashtagId")
    )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

    @Builder
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    private Article(UserAccount userAccount, String title, String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }

    public void changeUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void addHashtag(Hashtag hashtag) {
        this.getHashtags().add(hashtag);
    }

    public void addHashtags(Collection<Hashtag> hashtags) {
        this.getHashtags().addAll(hashtags);
    }

    public void clearHashtags() {
        this.getHashtags().clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return this.getId() != null && this.getId().equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
