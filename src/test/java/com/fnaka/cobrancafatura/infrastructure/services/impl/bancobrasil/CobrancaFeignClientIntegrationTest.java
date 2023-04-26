package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class CobrancaFeignClientIntegrationTest {

    @Autowired
    private OAuthClientService oAuthClientService;
    @Autowired
    private BancoBrasilCredential bancoBrasilCredential;
    @Autowired
    private CobrancaFeignClient cobrancaFeignClient;

    @Test
    void assertDependencies() {
        Assertions.assertNotNull(oAuthClientService);
        Assertions.assertNotNull(bancoBrasilCredential);
        Assertions.assertNotNull(cobrancaFeignClient);
    }

    @Test
    void givenValidNumeroTituloClienteAndCovenio_whenCallsDetalhaBoleto_shouldReturnCobranca() {
        // given
        final var token = oAuthClientService.generateToken();
        final var authorization = token.getBearerToken();
        final var expectedNumeroTituloCliente = "00031285573000000003";
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNumeroConvenio = "3128557";

        // when
        final var actualCobrancaResponse = cobrancaFeignClient.detalhaBoleto(
                authorization,
                expectedNumeroTituloCliente,
                expectedDevAppKey,
                expectedNumeroConvenio
        );

        // then
        Assertions.assertNotNull(actualCobrancaResponse);
        Assertions.assertNotNull(actualCobrancaResponse.codigoLinhaDigitavel());
    }

    @Test
    void givenInvalidNumeroTituloCliente_whenCallsDetalhaBoleto_shouldThrowsBadRequestException() {
        // given
        final var token = oAuthClientService.generateToken();
        final var authorization = token.getBearerToken();
        final var expectedNumeroTituloCliente = "00041285573100020000";
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNumeroConvenio = "3128557";
        final var expectedErrorMessage = "{\"errors\":[{\"code\":\"4678420.1\",\"message\":\"Campo nosso número preenchido com dados inválidos.\"}]}";

        // when
        final var actualException = Assertions.assertThrows(
                BadRequestException.class,
                () -> cobrancaFeignClient.detalhaBoleto(
                        authorization,
                        expectedNumeroTituloCliente,
                        expectedDevAppKey,
                        expectedNumeroConvenio
                )
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}