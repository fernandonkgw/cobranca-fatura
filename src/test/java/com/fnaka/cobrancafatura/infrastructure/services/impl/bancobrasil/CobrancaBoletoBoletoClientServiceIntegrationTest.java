package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.ServiceTest;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ServiceTest
class CobrancaBoletoBoletoClientServiceIntegrationTest {

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
    void givenAValidNossoNumeroAndConvenio_whenCallsFindByNossoNumeroAndConvenio_shouldReturnCobrancaBoletoRequisicao() {
        // given
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedConvenio = 3128557;

        // when
        final var actualCobrancaBoletoRequisicao = cobrancaBoletoClientService.findByNossoNumeroAndConvenio(expectedNossoNumero, expectedConvenio);

        // then
        Assertions.assertNotNull(actualCobrancaBoletoRequisicao);
        Assertions.assertNotNull(actualCobrancaBoletoRequisicao.cobrancaBoleto());
        Assertions.assertNotNull(actualCobrancaBoletoRequisicao.requisicao());

        verify(oAuthClientService).generateToken();
        verify(bancoBrasilCredential).getDeveloperApplicationKey();
        verify(cobrancaBoletoFeignClient).findByNossoNumeroAndConvenio(anyString(), anyString(), eq(expectedNossoNumero), eq(expectedConvenio));
    }

    @Test
    void givenAnInvalidNossoNumero_whenCallsFindByNossoNumeroAndConvenio_shouldReturnCobrancaBoletoRequisicao() {
        // given
        final var expectedNossoNumero = "00031285573900000008";
        final var expectedConvenio = 3128557;

        // when
        final var actualCobrancaBoletoRequisicao = cobrancaBoletoClientService.findByNossoNumeroAndConvenio(expectedNossoNumero, expectedConvenio);

        // then
        Assertions.assertNotNull(actualCobrancaBoletoRequisicao);
        Assertions.assertNull(actualCobrancaBoletoRequisicao.cobrancaBoleto());
        Assertions.assertNotNull(actualCobrancaBoletoRequisicao.requisicao());

        verify(oAuthClientService).generateToken();
        verify(bancoBrasilCredential).getDeveloperApplicationKey();
        verify(cobrancaBoletoFeignClient).findByNossoNumeroAndConvenio(anyString(), anyString(), eq(expectedNossoNumero), eq(expectedConvenio));
    }

    @Test
    void givenAValidNossoNumeroAndConvenio_whenCallsGeraPixBoleto_shouldReturnPixBoletoRequisicao() {
        // given
        final var expectedNossoNumero = "00031285573000000030"; // precisa registrar um novo boleto antes de executar o test
        final var expectedNumeroConvenio = 3128557;

        // when
        final var actualPixBoletoRequisicao = cobrancaBoletoClientService.createPix(
                expectedNossoNumero,
                expectedNumeroConvenio
        );

        // then
        Assertions.assertNotNull(actualPixBoletoRequisicao);
        final var actualPixBoleto = actualPixBoletoRequisicao.pixBoleto();
        Assertions.assertNotNull(actualPixBoleto);
        Assertions.assertNotNull(actualPixBoleto.url());
        Assertions.assertNotNull(actualPixBoleto.txId());
        Assertions.assertNotNull(actualPixBoleto.emv());
        final var requisicao = actualPixBoletoRequisicao.requisicao();
        Assertions.assertNotNull(requisicao);
        Assertions.assertNotNull(requisicao.url());
        Assertions.assertNotNull(requisicao.payloadRequest());
        Assertions.assertNotNull(requisicao.payloadResponse());
    }
}