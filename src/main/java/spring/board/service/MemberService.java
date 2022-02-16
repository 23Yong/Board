package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Member;
import spring.board.domain.MyPage;
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
    public Long join(Member member) {
        // 중복 검사
        isDuplicatedMember(member);

        MyPage myPage = new MyPage();
        member.setMyPage(myPage);

        return memberRepository.save(member);
    }

    private void isDuplicatedMember(Member member) {
        List<Member> members = memberRepository.findByLoginId(member.getLoginId());

        if(!members.isEmpty()) {

            throw new DuplicatedMemberException("이미 존재하는 회원입니다.");
        }
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
    public Member findByUserId(String id) {
        List<Member> members = memberRepository.findByLoginId(id);

        if(members.isEmpty()) {
            throw new UserNotFoundException("찾으려는 회원이 없습니다.");
        }

        return members.get(0);
    }

}
