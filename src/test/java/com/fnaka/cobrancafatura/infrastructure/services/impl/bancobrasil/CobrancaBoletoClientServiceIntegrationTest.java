package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.ServiceTest;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ServiceTest
class CobrancaBoletoClientServiceIntegrationTest {

    @Autowired
    private CobrancaBoletoClientService cobrancaBoletoClientService;

    @SpyBean
    private OAuthClientService oAuthClientService;

    @SpyBean
    private BancoBrasilCredential bancoBrasilCredential;

    @SpyBean
    private CobrancaBoletoFeignClient cobrancaBoletoFeignClient;

    @Test
    void assertDependencies() {
        Assertions.assertNotNull(cobrancaBoletoClientService);
    }

    @Test
    void givenAValidConvenioAndNossoNumero_whenCallsDetalhaBoleto_shouldReturnCobranca() {
        // given
        final var expectedConvenio = 3128557;
        final var expectedNossoNumero = "00031285573000000008";

        // when
        final var actualCobrancaBoleto = cobrancaBoletoClientService.detalhaCobrancaBoleto(expectedConvenio, expectedNossoNumero);

        // then
        Assertions.assertNotNull(actualCobrancaBoleto);
        Assertions.assertNotNull(actualCobrancaBoleto.codigoLinhaDigitavel());

        verify(oAuthClientService).generateToken();
        verify(bancoBrasilCredential).getDeveloperApplicationKey();
        verify(cobrancaBoletoFeignClient).detalhaBoleto(anyString(), eq(expectedNossoNumero), anyString(), eq(expectedConvenio));
    }

    @Test
    void givenAnInvalidNossoNumero_whenCallsDetalhaBoleto_shouldThrowsBadRequestException() {
        // given
        final var expectedConvenio = 3128557;
        final var expectedNossoNumero = "10031285573000000008";
        final var expectedErrorMessage = "Bad Request";
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos/10031285573000000008?gw-dev-app-key=daae06a486398c4dddca2c6ffcbdab1a&numeroConvenio=3128557";
        final var expectedResponseBody = "{\"errors\":[{\"code\":\"4678420.1\",\"message\":\"Campo nosso número preenchido com dados inválidos.\"}]}";

        // when
        final var actualException = Assertions.assertThrows(
                BadRequestException.class,
                () -> cobrancaBoletoClientService.detalhaCobrancaBoleto(expectedConvenio, expectedNossoNumero)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(expectedUrl, actualException.getUrl());
        Assertions.assertEquals(expectedResponseBody, actualException.getResponseBody());
        Assertions.assertNull(actualException.getRequestBody());

        verify(oAuthClientService).generateToken();
        verify(bancoBrasilCredential).getDeveloperApplicationKey();
        verify(cobrancaBoletoFeignClient).detalhaBoleto(anyString(), eq(expectedNossoNumero), anyString(), eq(expectedConvenio));
    }

    @Disabled
    @Test
    void givenAValidNossoNumeroAndConvenio_whenCallsGeraPixBoleto_shouldReturnPix() {
        // given
        final var expectedNossoNumero = "00031285573000000014"; // precisa registrar um novo boleto antes de executar o test
        final var expectedNumeroConvenio = 3128557;

        // when
        final var actualPix = cobrancaBoletoClientService.createPix(
                expectedNumeroConvenio,
                expectedNossoNumero
        );

        // then
        Assertions.assertNotNull(actualPix);
        Assertions.assertNotNull(actualPix.url());
        Assertions.assertNotNull(actualPix.txId());
        Assertions.assertNotNull(actualPix.emv());
    }


}