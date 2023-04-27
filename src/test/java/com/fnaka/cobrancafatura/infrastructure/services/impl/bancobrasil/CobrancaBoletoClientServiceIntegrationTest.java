package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.ServiceTest;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import org.junit.jupiter.api.Assertions;
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
        final var expectedErrorMessage = "{\"errors\":[{\"code\":\"4678420.1\",\"message\":\"Campo nosso número preenchido com dados inválidos.\"}]}";

        // when
        final var actualException = Assertions.assertThrows(
                BadRequestException.class,
                () -> cobrancaBoletoClientService.detalhaCobrancaBoleto(expectedConvenio, expectedNossoNumero)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(oAuthClientService).generateToken();
        verify(bancoBrasilCredential).getDeveloperApplicationKey();
        verify(cobrancaBoletoFeignClient).detalhaBoleto(anyString(), eq(expectedNossoNumero), anyString(), eq(expectedConvenio));
    }
}