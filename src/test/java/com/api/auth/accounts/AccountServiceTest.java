package com.api.auth.accounts;

import com.api.auth.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class AccountServiceTest extends BaseControllerTest {

    @Autowired AccountService accountService;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void findByUsername() {
        // given
        String password = "jbj112";
        String email = "jbj112@naver.com";
        Set<AccountRole> roles = new HashSet<>();

        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(roles)
                .build();

        // when
        this.accountService.saveAccount(account);
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername("jbj112@naver.com");

        // then
        assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test
    void findByUsernameFail() {
        // given
        String username = "jbj112@naver.com";

        // when
        try {
            accountService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            //then
            assertThat(e.getMessage()).containsSequence(username);
        }

    }
}