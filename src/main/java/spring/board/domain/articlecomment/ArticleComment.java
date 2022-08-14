package spring.board.domain.articlecomment;

import lombok.*;
import spring.board.domain.BaseEntity;
import spring.board.domain.article.Article;
import spring.board.domain.member.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ArticleComment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "article_comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public ArticleComment(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void addWriter(Member writer) {
        this.writer = writer;
    }
    public void addPost(Article article) {
        article.addReply(this);
        this.article = article;
    }
    public void editReply(String content) {
        this.content = content;
    }
}