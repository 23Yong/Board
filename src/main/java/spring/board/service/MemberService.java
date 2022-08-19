package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.common.security.oauth.dto.OAuthAttributes;
import spring.board.controller.dto.MemberDto;
import spring.board.controller.dto.MemberDto.PasswordUpdateRequest;
import spring.board.controller.dto.MemberDto.MemberSaveRequest;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.Role;
import spring.board.exception.member.DuplicatedMemberException;
import spring.board.exception.member.UserNotFoundException;
import spring.board.domain.member.UserRepository;

import javax.servlet.http.HttpSession;

import static spring.board.common.SessionConst.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final UserRepository userRepository;

    private final HttpSession session;

    @Transactional
    public Long save(MemberSaveRequest requestDto) {
        if (userRepository.existsByLoginId(requestDto.getUserId())) {
            throw DuplicatedMemberException.createDuplicatedMemberException();
        }

        UserAccount userAccount = requestDto.toEntity();
        userAccount.addMyPage();
        userRepository.save(userAccount);
        return userAccount.getId();
    }

    public UserAccount findByLoginId(String loginId) {
        UserAccount userAccount = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        return userAccount;
    }

    @Transactional
    public Long updateNickname(MemberDto.MemberUpdateRequest requestDto, String loginId) {
        String nickname = requestDto.getNickname();

        UserAccount userAccount = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        userAccount.updateNickname(nickname);

        session.setAttribute(SESSION_LOGIN, userAccount);
        return userAccount.getId();
    }

    @Transactional
    public Long updatePassword(String loginId, PasswordUpdateRequest requestDto) {
        UserAccount userAccount = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotFoundException("해당 아이디를 가진 회원이 존재하지 않습니다."));

        session.setAttribute(SESSION_LOGIN, userAccount);
        return userAccount.getId();
    }

    @Transactional
    public UserAccount saveOrUpdate(OAuthAttributes attributes) {
        UserAccount userAccount = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> {
                    entity.updateNickname(attributes.getName());
                    entity.updateProfileImageUrl(attributes.getPicture());
                    entity.updateRole(Role.USER);
                    return entity;
                })
                .orElse(attributes.toEntity());

        return userRepository.save(userAccount);
    }
}
