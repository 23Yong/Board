package spring.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import spring.board.domain.article.Article;
import spring.board.domain.member.Member;
import spring.board.domain.member.MemberRepository;
import spring.board.domain.article.ArticleRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.ArticleDto.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    ArticleService articleService;

    private MemberInfo createMemberInfoDto() {
        return MemberInfo.builder()
                .loginId("loginId")
                .nickname("nickname")
                .build();
    }

    private ArticleSaveRequest createPostSaveRequest() {
        return ArticleSaveRequest.builder()
                .title("title")
                .content("content")
                .build();
    }

    @DisplayName("회원이 게시글 작성에 성공한다.")
    @Test
    void savePost() {
        // given
        Member member = createMemberInfoDto().toEntity();

        ArticleSaveRequest articleSaveRequest = createPostSaveRequest();
        Article article = articleSaveRequest.toEntity(member);

        given(memberRepository.findByNickname("nickname")).willReturn(Optional.of(member));
        given(articleRepository.save(article)).willReturn(article);

        // when
        Long id = articleService.save(articleSaveRequest, "nickname");

        // then
        assertThat(member.getArticles().size()).isEqualTo(1);
    }
}