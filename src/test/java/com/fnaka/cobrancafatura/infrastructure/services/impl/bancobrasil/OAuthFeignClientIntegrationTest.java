package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.ServiceTest;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.utils.AuthorizationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@ServiceTest
class OAuthFeignClientIntegrationTest {

    @Autowired
    private OAuthFeignClient oAuthFeignClient;
    @Autowired
    private BancoBrasilCredential bancoBrasilCredential;

    @Test
    void assertDependencies() {
        Assertions.assertNotNull(oAuthFeignClient);
        Assertions.assertNotNull(bancoBrasilCredential);
    }

    @Test
    void givenValidCredencial_whenCallsGeraToken_shouldReturnToken() {
        // given
        final var username = bancoBrasilCredential.getClientId();
        final var password = bancoBrasilCredential.getClientSecret();
        final var authorization = AuthorizationUtils.basicAuth(username, password);

        final var formData = new HashMap<String, String>();
        formData.put("grant_type", "client_credentials");
//        formData.put("scope", "cobrancas.boletos-requisicao cobrancas.boletos-info");

        // when
        final var actualToken = oAuthFeignClient.token(authorization, formData);

        // then
        Assertions.assertNotNull(actualToken);
        Assertions.assertNotNull(actualToken.getAccessToken());
        Assertions.assertNotNull(actualToken.getTokenType());
        Assertions.assertNotNull(actualToken.getScope());

    }
}