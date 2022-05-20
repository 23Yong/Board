package spring.board.service;

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
import spring.board.controller.dto.MemberDto;
import spring.board.controller.dto.PostDto;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;
import spring.board.exception.post.PostNotFoundException;
import spring.board.domain.member.MemberRepository;
import spring.board.domain.post.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.PostDto.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    PostService postService;

    private MemberInfo createMemberInfoDto() {
        return MemberInfo.builder()
                .loginId("loginId")
                .nickname("nickname")
                .build();
    }

    private PostSaveRequest createPostSaveRequest() {
        return PostSaveRequest.builder()
                .title("title")
                .content("content")
                .build();
    }

    @DisplayName("회원이 게시글 작성에 성공한다.")
    @Test
    void savePost() {
        // given
        Member member = createMemberInfoDto().toEntity();

        PostSaveRequest postSaveRequest = createPostSaveRequest();
        Post post = postSaveRequest.toEntity(member);

        given(memberRepository.findByNickname("nickname")).willReturn(Optional.of(member));
        given(postRepository.save(post)).willReturn(post);

        // when
        Long id = postService.save(postSaveRequest, "nickname");

        // then
        assertThat(member.getPosts().size()).isEqualTo(1);
    }
}