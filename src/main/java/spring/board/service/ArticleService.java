package spring.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.Article;
import spring.board.domain.constants.SearchType;
import spring.board.domain.hashtag.Hashtag;
import spring.board.domain.hashtag.HashtagRepository;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.UserAccountRepository;
import spring.board.dto.ArticleDto;
import spring.board.domain.article.ArticleRepository;
import spring.board.dto.ArticleWithCommentsDto;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
    private final HashtagRepository hashtagRepository;

    private final HashtagService hashtagService;

    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }
        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable)
                    .map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable)
                    .map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable)
                    .map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable)
                    .map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtagNames(
                    Arrays.stream(searchKeyword.split(" ")).toList(),
                    pageable
            )
                    .map(ArticleDto::from);
        };
    }

    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    @Transactional
    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            if (article.getUserAccount().equals(userAccount)) {
                if (dto.title() != null) {
                    article.changeTitle(dto.title());
                }
                if (dto.content() != null) {
                    article.changeContent(dto.content());
                }
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패, 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }
    }

    @Transactional
    public void deleteArticle(long articleId, String userId) {
        Article article = articleRepository.getReferenceById(articleId);
        Set<Long> hashtagIds = article.getHashtags().stream()
                .map(Hashtag::getId)
                .collect(Collectors.toUnmodifiableSet());

        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
        articleRepository.flush();

        hashtagIds.forEach(hashtagService::deleteHashtagWithoutArticles);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtagNames(null, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return hashtagRepository.findAllHashtagNames();
    }

    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다. - articleId: " + articleId));
    }
}
