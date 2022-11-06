package spring.board.domain.article.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.board.domain.article.Article;
import spring.board.domain.hashtag.querydsl.HashtagRepositoryCustom;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {

    /**
     * @deprecated 해시태그 도메인을 따로 생성했기 때문에, 이 코드는 더이상 사용할 필요 없다.
     * @see HashtagRepositoryCustom findAllHashtagNames()
     * @return
     */
    @Deprecated
    List<String> findAllDistinctHashtags();

    Page<Article> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable);
}
