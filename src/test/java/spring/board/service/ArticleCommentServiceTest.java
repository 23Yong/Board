package spring.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import spring.board.domain.article.Article;
import spring.board.domain.hashtag.Hashtag;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.UserAccountRepository;
import spring.board.dto.ArticleCommentDto;
import spring.board.domain.article.ArticleRepository;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.articlecomment.ArticleCommentRepository;
import spring.board.dto.UserAccountDto;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @Mock
    private UserAccountRepository userAccountRepository;

    @DisplayName("게시글 ID로 조회하면 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        // when
        List<ArticleCommentDto> articleComments = sut.searchArticleComments(articleId);

        // then
        assertThat(articleComments)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
        // given
        ArticleCommentDto dto = createArticleCommentDto("comment");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(userAccountRepository.getReferenceById(dto.userAccountDto().userId())).willReturn(createUserAccount());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // when
        sut.saveArticleComment(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).should().getReferenceById(dto.userAccountDto().userId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).shouldHaveNoInteractions();
        then(articleCommentRepository).shouldHaveNoInteractions();
    }


    @DisplayName("부모 댓글 ID와 댓글 정보를 입력하면, 대댓글을 저장한다.")
    @Test
    void givenParentCommentIdAndArticleCommentInfo_whenSaving_thenSavesChildComment() {
        // given
        Long parentCommentId = 1L;
        ArticleComment parent = createArticleComment(parentCommentId, "댓글");
        ArticleCommentDto child = createArticleCommentDto(parentCommentId, "대댓글");
        given(articleRepository.getReferenceById(child.articleId())).willReturn(createArticle());
        given(userAccountRepository.getReferenceById(child.userAccountDto().userId())).willReturn(createUserAccount());
        given(articleCommentRepository.getReferenceById(child.parentCommentId())).willReturn(parent);

        // when
        sut.saveArticleComment(child);

        // then
        assertThat(child.parentCommentId()).isNotNull();
        then(articleRepository).should().getReferenceById(child.articleId());
        then(userAccountRepository).should().getReferenceById(child.userAccountDto().userId());
        then(articleCommentRepository).should().getReferenceById(child.parentCommentId());
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // Given
        String oldContent = "content";
        String updatedContent = "댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto dto = createArticleCommentDto(updatedContent);
        given(articleCommentRepository.getReferenceById(dto.id())).willReturn(articleComment);

        // When
        sut.updateArticleComment(dto);

        // Then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("댓글 ID를 입력하면 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // given
        Long articleCommentId = 1L;
        String userId = "23Yong";
        willDoNothing().given(articleCommentRepository).deleteByIdAndUserAccount_UserId(articleCommentId, userId);

        // when
        sut.deleteArticleComment(articleCommentId, userId);

        // then
        then(articleCommentRepository).should().deleteByIdAndUserAccount_UserId(articleCommentId, userId);
    }

    private ArticleCommentDto createArticleCommentDto(Long id, Long parentCommentId, String content) {
        return ArticleCommentDto.of(
                id,
                createUserAccountDto(),
                parentCommentId,
                content
        );
    }

    private ArticleCommentDto createArticleCommentDto(Long parentCommentId, String content) {
        return createArticleCommentDto(1L, parentCommentId, content);
    }

    private ArticleCommentDto createArticleCommentDto(String content) {
        return createArticleCommentDto(null, content);
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                LocalDateTime.now(),
                "23Yong",
                LocalDateTime.now(),
                "23Yong",
                "23Yong",
                "23Yong@email.com",
                "password",
                "nickname",
                "memo"
        );
    }

    private ArticleComment createArticleComment(Long id, String content) {
        ArticleComment articleComment = ArticleComment.of(
                createUserAccount(),
                content,
                createArticle()
        );

        ReflectionTestUtils.setField(articleComment, "id", id);
        return articleComment;
    }

    private ArticleComment createArticleComment(String content) {
        return createArticleComment(1L, content);
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "23Yong",
                "password",
                "23Yong@email.com",
                "23Yong",
                null
        );
    }

    private Hashtag createHashtag(Article article) {
        return Hashtag.of("java");
    }

    private Article createArticle() {
        Article article = Article.of(
                createUserAccount(),
                "title",
                "content"
        );
        ReflectionTestUtils.setField(article, "id", 1L);
        article.addHashtags(Set.of(createHashtag(article)));

        return article;
    }
}