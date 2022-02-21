package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Member;
import spring.board.exception.member.DuplicatedMemberException;
import spring.board.exception.member.UserNotFoundException;
import spring.board.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public void join(Member member) {
        if (memberRepository.existsByLoginId(member.getLoginId())) {
            throw DuplicatedMemberException.createDuplicatedMemberException();
        }

        member.addMyPage();
        memberRepository.save(member);
    }

    /**
     * 회원 전체 목록 조회
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 아이디로 회원 조회
     */
    public Member findByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        return member;
    }

}
