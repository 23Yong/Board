package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.UserAccountRepository;
import spring.board.dto.UserAccountDto;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public Optional<UserAccountDto> searchUser(String username) {
        return userAccountRepository.findById(username)
                .map(UserAccountDto::from);
    }

    @Transactional
    public UserAccountDto saveUser(String username, String password, String email, String nickname, String memo) {
        return UserAccountDto.from(
                userAccountRepository.save(UserAccount.of(username, password, email, nickname, memo, username))
        );
    }
}
