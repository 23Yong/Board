package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.UserAccountRepository;

import javax.servlet.http.HttpSession;

import java.util.List;

import static spring.board.common.SessionConst.*;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final HttpSession session;
    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("찾으려는 회원이 없습니다. : " + userId));

        session.setAttribute(SESSION_LOGIN, userAccount);
        return new User(userAccount.getUserId(), userAccount.getUserPassword(), List.of());

    }

    public UserAccount getLoginMember() {
        return (UserAccount) session.getAttribute(SESSION_LOGIN);
    }
}
