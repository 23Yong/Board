package spring.board.domain.article;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.domain.BaseEntity;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String hashtag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ArticleComment> replies = new ArrayList<>();

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

    public void changePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addReply(ArticleComment articleComment) {
        replies.add(articleComment);
    }
}
