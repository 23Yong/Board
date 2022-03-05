package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.controller.dto.MemberDto;
import spring.board.controller.dto.MemberDto.ChangePasswordRequest;
import spring.board.controller.dto.MemberDto.SaveRequest;
import spring.board.domain.Member;
import spring.board.exception.member.DuplicatedMemberException;
import spring.board.exception.member.UnauthenticatedUserException;
import spring.board.exception.member.UserNotFoundException;
import spring.board.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public void join(SaveRequest saveRequestDto) {
        if (memberRepository.existsByLoginId(saveRequestDto.getLoginId())) {
            throw DuplicatedMemberException.createDuplicatedMemberException();
        }

        Member member = saveRequestDto.toEntity();
        member.addMyPage();
        memberRepository.save(member);
    }

    /**
     * 아이디로 회원 조회
     */
    public Member findByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        return member;
    }

    public Member findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 회원이 존재하지 않습니다."));

        return member;
    }

    @Transactional
    public void updateNickname(MemberDto.EditMemberInfoRequest editMemberInfoRequest) {
        String loginId = editMemberInfoRequest.getLoginId();
        String changedNickname = editMemberInfoRequest.getNickname();

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        member.updateNickname(changedNickname);
    }

    @Transactional
    public void updatePassword(String loginId, ChangePasswordRequest changePasswordRequest) {
        String prevPassword = changePasswordRequest.getPrevPassword();
        String afterPassword = changePasswordRequest.getAfterPassword();

        if (!memberRepository.existsByLoginIdAndPassword(loginId, prevPassword)) {
            throw new UnauthenticatedUserException("이전 비밀번호가 일치하지 않습니다.");
        }

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        member.updatePassword(afterPassword);
    }

}
