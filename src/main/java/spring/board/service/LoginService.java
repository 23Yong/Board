package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.board.domain.member.Member;
import spring.board.domain.member.MemberDetails;
import spring.board.domain.member.MemberRepository;

import javax.servlet.http.HttpSession;

import static spring.board.common.SessionConst.*;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final HttpSession session;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("찾으려는 회원이 없습니다. : " + loginId));

        session.setAttribute(SESSION_LOGIN, member);
        return new MemberDetails(member);
    }

    public Member getLoginMember() {
        return (Member) session.getAttribute(SESSION_LOGIN);
    }
}
