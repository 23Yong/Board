package spring.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "id")
    private String userId;

    private String password;

    private String nickname;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "my_page_id")
    MyPage myPage;

    @OneToMany(mappedBy = "member")
    private List<Post> post;

}
