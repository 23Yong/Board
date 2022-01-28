package spring.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    private String nickname;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "my_page_id")
    private MyPage myPage;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    public void setMyPage(MyPage myPage) {
        this.myPage = myPage;
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setMember(this);
    }

    @Builder
    public Member(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }
}
