package spring.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시글_저장() throws Exception {
        // given
        Post post = new Post();

        // when
        Long postId = postRepository.save(post);

        // then
        assertThat(postId).isEqualTo(post.getId());
    }

    @Test
    public void 게시글_검색_식별자() throws Exception {
        // given
        Post post = new Post();
        Long postId = postRepository.save(post);

        // when
        Post findPost = postRepository.findOne(postId);

        // then
        assertThat(findPost).isEqualTo(post);
    }

    @Test
    public void 게시글_검색_제목() throws Exception {
        // given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .createdTime(LocalDateTime.now())
                .build();
        Long postId = postRepository.save(post);

        // when
        List<Post> findPosts = postRepository.findByTitle("title");

        // then
        assertThat(findPosts.get(0)).isEqualTo(post);
    }
}