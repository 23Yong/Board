package spring.board.domain.member;

import lombok.*;
import spring.board.domain.AuditingFields;
import spring.board.domain.article.Article;
import spring.board.domain.articlecomment.ArticleComment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {

    @Id
    @Column(length = 50)
    private String userId;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String userPassword;

    @Column(unique = true, nullable = false, length = 100)
    private String nickname;

    private String memo;

    @OneToMany(mappedBy = "userAccount")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount")
    private List<ArticleComment> replies = new ArrayList<>();

    public void addArticle(Article article) {
        articles.add(article);
    }

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo, null);
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo, String createdBy) {
        return new UserAccount(userId, userPassword, email, nickname, memo, createdBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }
}
