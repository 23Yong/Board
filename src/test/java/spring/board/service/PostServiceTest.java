package spring.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.exception.post.PostNotFoundException;
import spring.board.repository.MemberRepository;
import spring.board.repository.PostRepository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    PostService postService;

    @Test
    public void 게시글_작성() {
        // given
        Member member = createMember();
        Post post = createPost();
        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.of(member));
        given(postRepository.save(post)).willReturn(post);

        // when
        postService.registerPost(member.getLoginId(), post);

        // then
        then(postRepository).should(times(1)).save(post);
        then(memberRepository).should(times(1)).findByLoginId(member.getLoginId());
    }

    @DisplayName("게시글 아이디를 통해 게시글을 조회한다.")
    @Test
    public void 게시글_조회() {
        // given
        Post post = createPost();
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // when
        Post findPost = postService.findPost(1L);

        // then
        assertThat(findPost).isEqualTo(post);
        then(postRepository).should(times(1)).findById(1L);
    }

    @DisplayName("게시글 아이디가 없어 게시글 조회가 실패한다.")
    @Test
    public void 게시글_조회_실패() {
        // given
        // when
        // then
        assertThrows(PostNotFoundException.class, () -> postService.findPost(1L));
    }

    @Test
    public void 게시글_전체_조회() {
        // given
        List<Post> posts = savePosts();
        int totalSize = posts.size();
        PageRequest page = PageRequest.of(0, 10);
        Page<Post> result = new PageImpl<>(posts, page, totalSize);
        given(postRepository.findAll(page)).willReturn(result);

        // when
        Page<Post> allPosts = postService.findAllPosts(page);

        // then
        assertThat(result.getSize()).isEqualTo(allPosts.getSize());
        assertThat(result.getTotalElements()).isEqualTo(100);
        assertThat(result.getTotalPages()).isEqualTo(10);
    }

    @Test
    public void 게시글_수정() {
        // given
        Post post = createPost();
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // when
        postService.updatePost(1L, "changed title", "changed content");

        // then
        assertThat(post.getTitle()).isEqualTo("changed title");
        assertThat(post.getContent()).isEqualTo("changed content");
    }

    private Member createMember() {
        Member member = Member.builder()
                .loginId("userID")
                .password("password")
                .nickname("nickname")
                .build();
        return member;
    }

    private Post createPost() {
        Post post = Post.builder()
                .title("title")
                .content("content")
                .createdTime(LocalDateTime.now())
                .build();

        return post;
    }

    private List<Post> savePosts() {
        Member member = createMember();
        List<Post> posts = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Post post = Post.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .createdTime(LocalDateTime.now())
                    .build();

            posts.add(post);
        }

        return posts;
    }
}