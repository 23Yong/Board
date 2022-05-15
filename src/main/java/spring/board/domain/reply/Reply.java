package spring.board.domain.reply;

import lombok.*;
import spring.board.domain.BaseTimeEntity;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;

import javax.persistence.*;
import java.time.LocalDateTime;
import static javax.persistence.FetchType.*;
import static spring.board.controller.dto.ReplyDto.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Reply(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void addWriter(Member writer) {
        this.writer = writer;
    }
    public void addPost(Post post) {
        post.addReply(this);
        this.post = post;
    }
    public void editReply(String content) {
        this.content = content;
    }
}