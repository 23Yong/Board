package spring.board.domain.reply;

import lombok.*;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;
import static spring.board.controller.dto.ReplyDto.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reply {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Reply(Long id, String content, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.content = content;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public ReplyResponse toReplyResponse() {
        return ReplyResponse.builder()
                .id(this.id)
                .content(this.content)
                .updatedTime(this.updatedTime)
                .build();
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
