package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class OAuthClientServiceTest {

    @Autowired
    private OAuthClientService oAuthClientService;

    @Test
    void whenCallsGenerateToken_shouldReturnToken() {
        // when
        Token token = oAuthClientService.generateToken();

        // then
        Assertions.assertNotNull(token);
    }
}