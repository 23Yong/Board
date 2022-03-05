package spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.board.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByLoginIdAndPassword(String loginId, String password);

    boolean existsByLoginId(String loginId);

    boolean existsByLoginIdAndPassword(String loginId, String password);
}
