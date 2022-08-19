package spring.board.domain.member;

import lombok.*;
import spring.board.common.security.consts.OauthType;
import spring.board.domain.AuditingFields;
import spring.board.domain.MyPage;
import spring.board.domain.article.Article;
import spring.board.domain.articlecomment.ArticleComment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "userId"),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {

    @Id @GeneratedValue
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String userPassword;

    @Column(unique = true, nullable = false, length = 100)
    private String nickname;

    private String memo;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type")
    private OauthType oauthType;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "my_page_id")
    private MyPage myPage;

    @OneToMany(mappedBy = "userAccount")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<ArticleComment> replies = new ArrayList<>();

    public void addMyPage() {
        this.myPage = new MyPage();
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.userPassword = password;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
    }

    @Builder
    public UserAccount(String email, String userId, String userPassword, String nickname, Role role) {
        this.email = email;
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserAccount createOauth(String oauthId, String name, String email, String profileImageUrl, OauthType oauthType) {
        UserAccount userAccount = new UserAccount();
        userAccount.userId = oauthId;
        userAccount.nickname = name;
        userAccount.email = email;
        userAccount.profileImageUrl = profileImageUrl;
        userAccount.role = Role.USER;
        userAccount.oauthType = oauthType;
        return userAccount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserAccount userAccount)) return false;
        return id != null && obj.equals(userAccount.id);
    }
}
