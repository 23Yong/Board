package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.common.SessionConst;
import spring.board.common.security.oauth.dto.OAuthAttributes;
import spring.board.controller.dto.MemberDto;
import spring.board.controller.dto.MemberDto.PasswordUpdateRequest;
import spring.board.controller.dto.MemberDto.MemberSaveRequest;
import spring.board.domain.member.Member;
import spring.board.domain.member.Role;
import spring.board.exception.member.DuplicatedMemberException;
import spring.board.exception.member.UnauthenticatedUserException;
import spring.board.exception.member.UserNotFoundException;
import spring.board.domain.member.MemberRepository;

import javax.servlet.http.HttpSession;

import static spring.board.common.SessionConst.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final HttpSession session;

    @Transactional
    public Long save(MemberSaveRequest requestDto) {
        if (memberRepository.existsByLoginId(requestDto.getLoginId())) {
            throw DuplicatedMemberException.createDuplicatedMemberException();
        }
        
        requestDto.encodingPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member member = requestDto.toEntity();
        member.addMyPage();
        memberRepository.save(member);
        return member.getId();
    }

    public Member findByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        return member;
    }

    @Transactional
    public Long updateNickname(MemberDto.MemberUpdateRequest requestDto, String loginId) {
        String nickname = requestDto.getNickname();

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        member.updateNickname(nickname);

        session.setAttribute(SESSION_LOGIN, member);
        return member.getId();
    }

    @Transactional
    public Long updatePassword(String loginId, PasswordUpdateRequest requestDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        if (passwordEncoder.matches(member.getPassword(), requestDto.getPrevPassword())) {
            throw new UnauthenticatedUserException("이전 비밀번호가 일치하지 않습니다.");
        }

        String afterPassword = passwordEncoder.encode(
                requestDto.getAfterPassword());

        member.updatePassword(afterPassword);

        session.setAttribute(SESSION_LOGIN, member);
        return member.getId();
    }

    @Transactional
    public Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> {
                    entity.updateNickname(attributes.getName());
                    entity.updateProfileImageUrl(attributes.getPicture());
                    entity.updateRole(Role.USER);
                    return entity;
                })
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
