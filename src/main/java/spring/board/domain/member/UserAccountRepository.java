package spring.board.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserId(String userId);

    Optional<UserAccount> findByNickname(String nickname);

    Optional<UserAccount> findByEmail(String email);

    boolean existsByUserId(String userId);
}
