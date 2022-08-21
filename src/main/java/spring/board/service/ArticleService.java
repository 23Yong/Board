package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.SearchType;
import spring.board.dto.ArticleDto;
import spring.board.dto.ArticleUpdateDto;
import spring.board.domain.member.UserAccountRepository;
import spring.board.domain.article.ArticleRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword) {
        return Page.empty();
    }

    public ArticleDto searchArticle(long articleId) {
        return null;
    }

    @Transactional
    public void updateArticle(ArticleDto dto) {

    }

    @Transactional
    public void updateArticle(long articleId, ArticleUpdateDto dto) {

    }

    @Transactional
    public void deleteArticle(long articleId) {

    }
}
