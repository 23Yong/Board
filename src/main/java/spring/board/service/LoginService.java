package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Member;
import spring.board.exception.member.UserNotFoundException;
import spring.board.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        List<Member> members = memberRepository.findByLoginIdAndPassword(loginId, password);

        if (members.isEmpty()) {
            throw new UserNotFoundException("찾으려는 회원이 없습니다.");
        }

        return members.get(0);
    }
}
