package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.board.domain.Member;
import spring.board.exception.NoFindException;
import spring.board.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param member
     * @return
     */
    public Long join(Member member) {
        // 중복 검사
        isDuplicatedMember(member);

        return memberRepository.save(member);
    }

    private void isDuplicatedMember(Member member) {
        List<Member> members = memberRepository.findByMemberId(member.getUserId());

        if(!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 목록 조회
     * @return
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 아이디로 회원 조회
     * @param id
     * @return
     */
    public Member findByUserId(String id) {
        List<Member> members = memberRepository.findByMemberId(id);

        if(members.isEmpty()) {
            throw new NoFindException("찾으려는 회원이 없습니다.");
        }

        return members.get(0);
    }
}
