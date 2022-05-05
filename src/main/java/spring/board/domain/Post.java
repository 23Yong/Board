package spring.board.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private LocalDateTime createdTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Reply> replies;

    public void setMember(Member member) {
        this.member = member;
    }

    public Post() {}

    @Builder
    public Post(String title, String content, LocalDateTime createdTime) {
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
    }

    public void changePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addReply(Reply reply) {
        replies.add(reply);
    }
}
