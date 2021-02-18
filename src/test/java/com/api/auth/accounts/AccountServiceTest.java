package com.api.auth.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired AccountService accountService;

    @Autowired AccountRepository accountRepository;

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
        this.accountRepository.save(account);
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername("jbj112@naver.com");

        // then
        assertThat(userDetails.getPassword()).isEqualTo(password);
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