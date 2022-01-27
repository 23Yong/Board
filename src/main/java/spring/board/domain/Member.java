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

    @Column(name = "id", unique = true)
    private String userId;

    private String password;

    private String nickname;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "my_page_id")
    MyPage myPage;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    public void setMyPage(MyPage myPage) {
        this.myPage = myPage;
        myPage.setMember(this);
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setMember(this);
    }

    @Builder
    public Member(String userId, String password, String nickname) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }
}
