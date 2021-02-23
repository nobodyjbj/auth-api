package com.api.auth.config;

import com.api.auth.accounts.Account;
import com.api.auth.accounts.AccountRole;
import com.api.auth.accounts.AccountService;
import com.api.auth.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;

import java.util.Set;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @Description("인증 토큰 발급 테스트")
    void getAuthTokenTest() throws Exception {
        // given
        String username = "jbj112@naver.com";
        String password ="jbj112";
        Account account =  Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        this.accountService.saveAccount(account);

        String clientId = "AuthApi";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}