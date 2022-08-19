package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.Article;
import spring.board.domain.member.UserAccount;
import spring.board.exception.member.UserNotFoundException;
import spring.board.exception.article.ArticleNotFoundException;
import spring.board.domain.member.UserRepository;
import spring.board.domain.article.ArticleRepository;

import static spring.board.controller.dto.ArticleDto.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(ArticleSaveRequest requestDto, String nickname) {
        UserAccount userAccount = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserNotFoundException("찾으려는 회원이 없습니다."));

        Article article = articleRepository.save(requestDto.toEntity(userAccount));
        article.setUserAccount(userAccount);

        return article.getId();
    }

    @Transactional
    public Long update(ArticleUpdateRequest request) {
        Article article = articleRepository.findById(request.getId())
                .orElseThrow(() -> new ArticleNotFoundException("찾으려는 게시물이 없습니다."));

        article.changeArticle(request.getTitle(), request.getContent());
        return article.getId();
    }

    @Transactional
    public Long delete(Long id) {
        articleRepository.deleteById(id);
        return id;
    }

    public Article findArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("해당 아이디를 가진 게시글이 존재하지 않습니다."));
    }

    public Page<ArticleInfo> findAllArticles(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 10);

        return articleRepository.findAll(pageable)
                .map(ArticleInfo::new);
    }
}
