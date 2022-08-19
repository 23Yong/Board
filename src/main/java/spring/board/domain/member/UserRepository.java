package spring.board.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByLoginId(String loginId);

    Optional<UserAccount> findByNickname(String nickname);

    Optional<UserAccount> findByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByLoginIdAndPassword(String loginId, String password);
}
