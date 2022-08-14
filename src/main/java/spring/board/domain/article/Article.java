package spring.board.domain.article;

import lombok.*;
import spring.board.domain.BaseEntity;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.member.Member;

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
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 10000, nullable = false)
    private String content;

    private String hashtag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ArticleComment> articleComments = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.addPost(this);
    }

    @Builder
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
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
