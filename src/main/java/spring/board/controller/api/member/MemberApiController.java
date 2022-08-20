package spring.board.controller.api.member;

import lombok.RequiredArgsConstructor;
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

    @PutMapping("/{userId}")
    public Long updateNickname(@RequestBody MemberUpdateRequest requestDto, @PathVariable String userId) {
        return memberService.updateNickname(requestDto, userId);
    }

    @PutMapping("/{userId}/password")
    public Long updatePassword(@RequestBody PasswordUpdateRequest requestDto, @PathVariable String userId) {
        Long id =  memberService.updatePassword(userId, requestDto);

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userId, requestDto.getAfterPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return id;
    }
}
