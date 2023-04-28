package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.ServiceTest;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class CobrancaBoletoFeignClientIntegrationTest {

    @Autowired
    private OAuthClientService oAuthClientService;
    @Autowired
    private BancoBrasilCredential bancoBrasilCredential;
    @Autowired
    private CobrancaBoletoFeignClient cobrancaBoletoFeignClient;

    @Test
    void assertDependencies() {
        Assertions.assertNotNull(oAuthClientService);
        Assertions.assertNotNull(bancoBrasilCredential);
        Assertions.assertNotNull(cobrancaBoletoFeignClient);
    }

    @Test
    void givenValidNossoNumeroAndCovenio_whenCallsDetalhaBoleto_shouldReturnCobranca() {
        // given
        final var token = oAuthClientService.generateToken();
        final var authorization = token.getBearerToken();
        final var expectedNossoNumero = "00031285573000000003";
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNumeroConvenio = 3128557;

        // when
        final var actualCobrancaResponse = cobrancaBoletoFeignClient.detalhaBoleto(
                authorization,
                expectedNossoNumero,
                expectedDevAppKey,
                expectedNumeroConvenio
        );

        // then
        Assertions.assertNotNull(actualCobrancaResponse);
        Assertions.assertNotNull(actualCobrancaResponse.codigoLinhaDigitavel());
    }

    @Test
    void givenInvalidNossoNumero_whenCallsDetalhaBoleto_shouldThrowsBadRequestException() {
        // given
        final var token = oAuthClientService.generateToken();
        final var authorization = token.getBearerToken();
        final var expectedNossoNumero = "00041285573100020000";
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNumeroConvenio = 3128557;
        final var expectedErrorMessage = "Bad Request";
        final var expectedResponseBody = "{\"errors\":[{\"code\":\"4678420.1\",\"message\":\"Campo nosso número preenchido com dados inválidos.\"}]}";
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos/00041285573100020000?gw-dev-app-key=daae06a486398c4dddca2c6ffcbdab1a&numeroConvenio=3128557";

        // when
        final var actualException = Assertions.assertThrows(
                BadRequestException.class,
                () -> cobrancaBoletoFeignClient.detalhaBoleto(
                        authorization,
                        expectedNossoNumero,
                        expectedDevAppKey,
                        expectedNumeroConvenio
                )
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(expectedResponseBody, actualException.getResponseBody());
        Assertions.assertEquals(expectedUrl, actualException.getUrl());
    }

    @Test
    void givenAValidNossoNumeroAndConvenio_whenCallsGeraPixBoleto_shouldReturnPix() {
        // given
        final var token = oAuthClientService.generateToken();
        final var bearerToken = token.getBearerToken();
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNossoNumero = "00031285573000000012"; // precisa registrar um novo boleto antes de executar o test
        final var expectedNumeroConvenio = 3128557;

        final var request = new GeraPixBoletoRequest(expectedNumeroConvenio);

        // when
        final var actualResponse = cobrancaBoletoFeignClient.geraPixBoleto(
                bearerToken,
                expectedDevAppKey,
                expectedNossoNumero,
                request
        );

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertNotNull(actualResponse.chave());
        Assertions.assertNotNull(actualResponse.url());
        Assertions.assertNotNull(actualResponse.txId());
        Assertions.assertNotNull(actualResponse.emv());
    }

    @Test
    void givenAnInvalidNossoNumero_whenCallsGeraPixBoleto_shouldThrowsBadRequestException() {
        // given
        final var token = oAuthClientService.generateToken();
        final var bearerToken = token.getBearerToken();
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNossoNumero = "00031285573000000012";
        final var expectedNumeroConvenio = 3128557;
        final var expectedRequestBody = "{\"numeroConvenio\":3128557}";
        final var expectedErrorMessage = "Bad Request";
        final var expectedResponseBody = "{\"erros\":[{\"codigo\":\"4432632\",\"versao\":\"1\",\"mensagem\":\"Já existe Pix cadastrado para o boleto\",\"ocorrencia\"";
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos/00031285573000000012/gerar-pix?gw-dev-app-key=daae06a486398c4dddca2c6ffcbdab1a";

        final var request = new GeraPixBoletoRequest(expectedNumeroConvenio);

        // when
        final var actualException = Assertions.assertThrows(
                BadRequestException.class,
                () -> cobrancaBoletoFeignClient.geraPixBoleto(
                        bearerToken,
                        expectedDevAppKey,
                        expectedNossoNumero,
                        request
                )
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(expectedRequestBody, actualException.getRequestBody());
        Assertions.assertTrue(actualException.getResponseBody().contains(expectedResponseBody));
        Assertions.assertEquals(expectedUrl, actualException.getUrl());
    }
}