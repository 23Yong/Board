package spring.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.Article;
import spring.board.domain.article.ArticleRepository;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.domain.articlecomment.ArticleCommentRepository;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.UserAccountRepository;
import spring.board.dto.ArticleCommentDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream().map(ArticleCommentDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            ArticleComment articleComment = dto.toEntity(article, userAccount);

            if (dto.parentCommentId() != null) {
                ArticleComment parentComment = articleCommentRepository.getReferenceById(dto.parentCommentId());
                parentComment.addChildComment(articleComment);
            } else {
                articleCommentRepository.save(articleComment);
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    @Transactional
    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            if (dto.content() != null) {
                articleComment.changeContent(dto.content());
            }
        } catch(EntityNotFoundException e) {
            log.warn("댓글 업데이트 실. 댓글을 찾을 수 없습니다 - dto:{}", dto);
        }
    }

    @Transactional
    public void deleteArticleComment(Long articleCommentId, String userId) {
        articleCommentRepository.deleteByIdAndUserAccount_UserId(articleCommentId, userId);
    }
}