package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.common.SessionConst;
import spring.board.domain.member.Member;
import spring.board.exception.member.CredentialException;
import spring.board.domain.member.MemberRepository;

import javax.servlet.http.HttpSession;

import static spring.board.common.SessionConst.*;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final HttpSession session;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public void login(String loginId, String password) {
        Member member = memberRepository.findByLoginIdAndPassword(loginId, password)
                .orElseThrow(() -> CredentialException.createCredentialException());

        session.setAttribute(SESSION_LOGIN, member);
    }

    public void logout() {
        session.removeAttribute(SESSION_LOGIN);
    }

    public Member getLoginMember() {
        return (Member) session.getAttribute(SESSION_LOGIN);
    }
}
