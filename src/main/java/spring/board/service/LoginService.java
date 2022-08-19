package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.MemberDetails;
import spring.board.domain.member.UserRepository;

import javax.servlet.http.HttpSession;

import static spring.board.common.SessionConst.*;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final HttpSession session;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        UserAccount userAccount = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("찾으려는 회원이 없습니다. : " + loginId));

        session.setAttribute(SESSION_LOGIN, userAccount);
        return new MemberDetails(userAccount);
    }

    public UserAccount getLoginMember() {
        return (UserAccount) session.getAttribute(SESSION_LOGIN);
    }
}
