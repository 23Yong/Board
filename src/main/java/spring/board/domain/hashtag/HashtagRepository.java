package spring.board.domain.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import spring.board.domain.hashtag.querydsl.HashtagRepositoryCustom;

@RepositoryRestResource
public interface HashtagRepository extends
        JpaRepository<Hashtag, Long>,
        HashtagRepositoryCustom,
        QuerydslPredicateExecutor<Hashtag> {
}
