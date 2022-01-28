package spring.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Member;
import spring.board.exception.NoFindException;
import spring.board.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() throws Exception {
        // given
        Member member = createMember("userID", "password", "nickname");

        // when
        Long memberId = memberService.join(member);

        // then
        assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    public void 회원가입_실패() throws Exception {
        // given
        Member member1 = createMember("userID", "password", "nickname");
        Member member2 = createMember("userID", "password2", "nickname");

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

    @Test
    public void 회원_전체_목록() throws Exception {
        // given
        Member member1 = createMember("userID", "password", "nickname");
        Member member2 = createMember("userID2", "password2", "nickname");

        memberService.join(member1);
        memberService.join(member2);

        // when
        List<Member> members = memberService.findAll();

        // then
        assertThat(members.get(0)).isEqualTo(member1);
        assertThat(members.get(1)).isEqualTo(member2);
    }

    @Test
    public void 회원_검색_아이디() throws Exception {
        // given
        Member member1 = createMember("userID", "password", "nickname");
        Member member2 = createMember("userID2", "password2", "nickname");

        memberService.join(member1);
        memberService.join(member2);

        // when
        Member member = memberService.findByUserId("userID");

        // then
        assertThat(member).isEqualTo(member1);
    }

    @Test
    public void 회원_검색_실패() throws Exception {
        // given
        Member member1 = createMember("userID", "password", "nickname");
        Member member2 = createMember("userID2", "password2", "nickname");

        memberService.join(member1);
        memberService.join(member2);

        // when

        // then
        assertThrows(NoFindException.class, () -> memberService.findByUserId("userID3"));
    }

    private Member createMember(String userID, String password, String nickname) {
        Member member = Member.builder()
                .loginId(userID)
                .password(password)
                .nickname(nickname)
                .build();
        return member;
    }
}