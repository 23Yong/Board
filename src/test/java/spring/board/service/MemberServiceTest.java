package spring.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.board.controller.dto.MemberDto.SaveRequest;
import spring.board.domain.member.Member;
import spring.board.exception.member.DuplicatedMemberException;
import spring.board.exception.member.UserNotFoundException;
import spring.board.domain.member.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void 회원가입_성공() {
        // given
        SaveRequest saveRequest = createMemberDto();
        Member member = saveRequest.toEntity();
        given(memberRepository.findByLoginId(saveRequest.getLoginId()))
                .willReturn(Optional.of(member));

        // when
        memberService.join(saveRequest);

        // then
        assertThat(Optional.of(member)).isEqualTo(memberRepository.findByLoginId(saveRequest.getLoginId()));
    }

    @DisplayName("중복된 로그인 아이디로 인해 회원가입이 실패한다.")
    @Test
    public void 회원가입_실패() {
        // given
        SaveRequest saveRequest = createMemberDto();
        Member member = createMember();
        given(memberRepository.existsByLoginId(member.getLoginId()))
                .willReturn(true);

        // when
        // then
        assertThrows(DuplicatedMemberException.class, () -> memberService.join(saveRequest));
        then(memberRepository).should(times(0)).save(member);
    }

    @Test
    public void 로그인아이디_회원조회() {
        // given
        Member member = createMember();
        given(memberRepository.findByLoginId(member.getLoginId()))
                .willReturn(Optional.of(member));

        // when
        Member findMember = memberService.findByLoginId(member.getLoginId());

        // then
        assertThat(member).isEqualTo(findMember);
        then(memberRepository).should(times(1)).findByLoginId(member.getLoginId());
    }

    @DisplayName("로그인아이디를 통해 조회한 회원이 없어서 실패한다.")
    @Test
    public void 로그인아이디_회원조회_실패() {
        // given
        Member member = createMember();

        // when
        // then
        assertThrows(UserNotFoundException.class, () -> memberService.findByLoginId(member.getLoginId()));
        then(memberRepository).should(times(1)).findByLoginId(member.getLoginId());
    }

    private SaveRequest createMemberDto() {
        return SaveRequest.builder()
                .loginId("loginId")
                .password("testPassword")
                .nickname("nickname")
                .build();
    }

    private Member createMember() {
        return createMemberDto().toEntity();
    }
}