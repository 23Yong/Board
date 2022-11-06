package spring.board.domain.hashtag.querydsl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import spring.board.domain.hashtag.Hashtag;
import spring.board.domain.hashtag.QHashtag;

import java.util.List;

public class HashtagRepositoryCustomImpl extends QuerydslRepositorySupport implements HashtagRepositoryCustom {

    public HashtagRepositoryCustomImpl() {
        super(Hashtag.class);
    }

    @Override
    public List<String> findAllHashtagNames() {
        QHashtag hashtag = QHashtag.hashtag;

        return from(hashtag)
                .select(hashtag.hashtagName)
                .fetch();
    }
}
