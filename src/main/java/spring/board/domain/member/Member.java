package spring.board.domain.member;

import lombok.*;
import spring.board.common.security.consts.OauthType;
import spring.board.domain.BaseTimeEntity;
import spring.board.domain.MyPage;
import spring.board.domain.post.Post;
import spring.board.domain.reply.Reply;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

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

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Reply> replies = new ArrayList<>();

    public void addMyPage() {
        this.myPage = new MyPage();
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    @Builder
    public Member(String email, String loginId, String password, String nickname, Role role) {
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public static Member createOauth(String oauthId, String name, String email, String profileImageUrl, OauthType oauthType) {
        Member member = new Member();
        member.loginId = oauthId;
        member.nickname = name;
        member.email = email;
        member.profileImageUrl = profileImageUrl;
        member.role = Role.USER;
        member.oauthType = oauthType;
        return member;
    }
}
