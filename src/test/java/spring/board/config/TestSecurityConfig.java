package spring.board.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import spring.board.common.config.SecurityConfig;
import spring.board.domain.member.UserAccountRepository;
import spring.board.dto.UserAccountDto;
import spring.board.service.UserAccountService;

import java.util.Optional;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean
    private UserAccountService userAccountService;

    @BeforeTestMethod
    void securitySetUp() {
        given(userAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));
        given(userAccountService.saveUser(anyString(), anyString(), anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "23YongTest",
                "pw",
                "23Yong-test@email.com",
                "23Yong-test",
                "test memo"
        );
    }
}
