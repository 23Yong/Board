package spring.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.board.domain.article.Article;
import spring.board.domain.member.UserAccount;
import spring.board.dto.ArticleCommentDto;
import spring.board.domain.article.ArticleRepository;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.articlecomment.ArticleCommentRepository;
import spring.board.dto.ArticleCommentUpdateDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleCommentRepository articleCommentRepository;
    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글 ID로 조회하면 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // given
        Long articleId = 1L;
        UserAccount userAccount = UserAccount.of("23Yong", "pw", null, null, null);
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(Article.of(userAccount, "title", "content", "#Java")));

        // when
        List<ArticleCommentDto> articleComments = sut.searchArticleComments(articleId);

        // then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
        // given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // when
        sut.saveArticleComment(ArticleCommentDto.of(LocalDateTime.now(), "23Yong", LocalDateTime.now(), "23Yong", "content"));

        // then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 ID와 수정정보를 입력하면 댓글을 수정한다.")
    @Test
    void givenArticleCommentIdAndModifiedInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // when
        sut.updateArticleComment(1L, ArticleCommentUpdateDto.of(LocalDateTime.now(), "23Yong", LocalDateTime.now(), "23Yong", "content"));

        // then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 ID를 입력하면 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // given
        willDoNothing().given(articleCommentRepository).delete(any(ArticleComment.class));

        // when
        sut.deleteArticleComment(1L);

        // then
        then(articleCommentRepository).should().delete(any(ArticleComment.class));
    }
}