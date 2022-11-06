package spring.board.domain.hashtag.querydsl;

import java.util.List;

public interface HashtagRepositoryCustom {

    List<String> findAllHashtagNames();
}
