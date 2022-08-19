package spring.board.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spring.board.common.security.AuthenticationEntryPointImpl;
import spring.board.common.security.oauth.CustomOAuth2UserService;
import spring.board.domain.member.Role;
import spring.board.service.LoginService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginService loginService;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/login", "/members/new")
                            .permitAll()
                        .antMatchers("/api/**", "members/{loginId:[\\w+]}/**").hasRole(Role.USER.name())
                        .anyRequest()
                            .authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                            .usernameParameter("loginId")
                            .passwordParameter("password")
                    .and()
                        .logout()
                        .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        /*http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);*/
    }
}
