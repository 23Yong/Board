package spring.board.config;

import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import spring.board.common.config.SecurityConfig;
import spring.board.domain.member.UserAccount;
import spring.board.domain.member.UserAccountRepository;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean
    UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "23YongTest",
                "pw",
                "23Yong-test@email.com",
                "23Yong-test",
                "test memo"
                )
        ));
    }
}
