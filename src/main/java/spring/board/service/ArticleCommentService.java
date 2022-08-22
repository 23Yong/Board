package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.ArticleRepository;
import spring.board.dto.ArticleCommentDto;
import spring.board.domain.articlecomment.ArticleCommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return List.of();
    }

    @Transactional
    public void saveArticleComment(ArticleCommentDto dto) {

    }

    @Transactional
    public void updateArticleComment(ArticleCommentDto dto) {

    }

    @Transactional
    public void deleteArticleComment(long articleCommentId) {

    }
}