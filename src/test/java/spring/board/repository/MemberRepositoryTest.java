package spring.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원_저장() throws Exception {
        // given
        Member member = createMember("회원 아이디", "회원 비밀번호", "회원 별명");

        // when
        Long savedId = memberRepository.save(member);

        // then
        assertThat(savedId).isEqualTo(member.getId());
    }

    @Test
    public void 회원_검색_식별자() throws Exception {
        // given
        Member member = createMember("회원 아이디", "회원 비밀번호", "회원 별명");
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findOne(member.getId());

        // then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 회원_검색_아이디() throws Exception {
        // given
        Member member1 = createMember("회원1", "비밀번호1", "별명1");
        Member member2 = createMember("회원2", "비밀번호2", "별명2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findByLoginId("회원1");

        // then
        assertThat(members.get(0)).isEqualTo(member1);
    }

    @Test
    public void 회원_전체_목록_조회() throws Exception {
        // given
        Member member1 = createMember("회원 아이디", "회원 비밀번호", "회원 별명");
        Member member2 = createMember("아아디", "비밀번호", "별명");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.get(0)).isEqualTo(member1);
        assertThat(members.get(1)).isEqualTo(member2);
    }

    private Member createMember(String memberId, String password, String nickname) {
        Member member = Member.builder()
                .loginId(memberId)
                .password(password)
                .nickname(nickname)
                .build();
        return member;
    }
}