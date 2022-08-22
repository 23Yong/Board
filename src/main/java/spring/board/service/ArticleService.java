package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.SearchType;
import spring.board.dto.ArticleDto;
import spring.board.domain.member.UserAccountRepository;
import spring.board.domain.article.ArticleRepository;
import spring.board.dto.ArticleWithCommentsDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }

    @Transactional
    public void saveArticle(ArticleDto dto) {

    }

    @Transactional
    public void updateArticle(ArticleDto dto) {

    }

    @Transactional
    public void deleteArticle(long articleId) {

    }
}
