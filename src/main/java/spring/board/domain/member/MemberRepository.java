package spring.board.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.board.common.security.consts.OauthType;
import spring.board.domain.member.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByLoginIdAndPassword(String loginId, String password);
}
