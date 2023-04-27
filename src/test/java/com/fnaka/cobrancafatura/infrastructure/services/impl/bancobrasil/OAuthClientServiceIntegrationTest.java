package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class OAuthClientServiceIntegrationTest {

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