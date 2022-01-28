package spring.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Member;
import spring.board.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostService postService;
    @Autowired MemberService memberService;

    @Test
    public void 게시글_작성() throws Exception {
        // given
        Member member = createMember("userID", "password", "nickname");
        Post post = createPost("title", "content");

        Long memberId = memberService.join(member);

        // when
        Long postId = postService.registerPost(memberId, post);

        // then
        assertThat(postId).isEqualTo(post.getId());
    }

    @Test
    public void 게시글_조회() throws Exception {
        // given
        Member member = createMember("userID", "password", "nickname");
        Post post = createPost("title", "content");

        Long memberId = memberService.join(member);
        Long postId = postService.registerPost(memberId, post);

        // when
        Post findPost = postService.findPost(postId);

        // then
        assertThat(findPost).isEqualTo(post);
    }

    @Test
    public void 게시글_전체_조회() throws Exception {
        // given
        Member member = createMember("userID", "password", "nickname");
        Post post1 = createPost("title", "content");
        Post post2 = createPost("title2", "content");

        Long memberId = memberService.join(member);
        Long postId1 = postService.registerPost(memberId, post1);
        Long postId2 = postService.registerPost(memberId, post2);

        // when
        List<Post> findPosts = postService.findAllPosts();

        // then
        assertThat(findPosts.get(0)).isEqualTo(post1);
        assertThat(findPosts.get(1)).isEqualTo(post2);
    }

    @Test
    public void 게시글_수정() throws Exception {
        // given
        Member member = createMember("userID", "password", "nickname");
        Post post = createPost("title", "content");

        Long memberId = memberService.join(member);
        Long postId = postService.registerPost(memberId, post);

        // when
        postService.updatePost(postId, "title", "changedContent");

        // then
        assertThat(post.getContent()).isEqualTo("changedContent");
    }

    private Member createMember(String userID, String password, String nickname) {
        Member member = Member.builder()
                .loginId(userID)
                .password(password)
                .nickname(nickname)
                .build();
        return member;
    }

    private Post createPost(String title, String content) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .createdTime(LocalDateTime.now())
                .build();
        return post;
    }
}