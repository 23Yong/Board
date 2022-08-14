package spring.board.controller.api.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.board.service.MemberService;

import static spring.board.controller.dto.MemberDto.*;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberApiController {

    private final MemberService memberService;

    private final AuthenticationManager authenticationManager;

    @PutMapping("/{loginId}")
    public Long updateNickname(@RequestBody MemberUpdateRequest requestDto, @PathVariable String loginId) {
        return memberService.updateNickname(requestDto, loginId);
    }

    @PutMapping("/{loginId}/password")
    public Long updatePassword(@RequestBody PasswordUpdateRequest requestDto, @PathVariable String loginId) {
        Long id =  memberService.updatePassword(loginId, requestDto);

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, requestDto.getAfterPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return id;
    }
}