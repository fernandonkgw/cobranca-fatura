package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.ServiceTest;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ServiceTest
class CobrancaBoletoBoletoFeignClientIntegrationTest {

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
    void givenValidNossoNumeroAndCovenio_whenCallsFindByNossoNumeroAndConvenio_shouldReturnResponse() throws IOException {
        // given
        final var token = oAuthClientService.generateToken();
        final var authorization = token.getBearerToken();
        final var expectedNossoNumero = "00031285573000000003";
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNumeroConvenio = 3128557;
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos/00031285573000000003?gw-dev-app-key=daae06a486398c4dddca2c6ffcbdab1a&numeroConvenio=3128557";
        final var expectedResponseBody = "{\"codigoLinhaDigitavel\":\"00190000090312855730700000003178193210000001234\",\"textoEmailPagador\":\"\",\"textoMensagemBloquetoTitulo\":\"\",\"codigoTipoMulta\":0,\"codigoCanalPagamento\":0,\"numeroContratoCobranca\":19581316,\"codigoTipoInscricaoSacado\":2,\"numeroInscricaoSacadoCobranca\":74910037000193,\"codigoEstadoTituloCobranca\":1,\"codigoTipoTituloCobranca\":2,\"codigoModalidadeTitulo\":1,\"codigoAceiteTituloCobranca\":\"A\",\"codigoPrefixoDependenciaCobrador\":794,\"codigoIndicadorEconomico\":9,\"numeroTituloCedenteCobranca\":\"\",\"codigoTipoJuroMora\":0,\"dataEmissaoTituloCobranca\":\"01.04.2023\",\"dataRegistroTituloCobranca\":\"03.04.2023\",\"dataVencimentoTituloCobranca\":\"15.04.2023\",\"valorOriginalTituloCobranca\":12.34,\"valorAtualTituloCobranca\":12.34,\"valorPagamentoParcialTitulo\":0,\"valorAbatimentoTituloCobranca\":0,\"percentualImpostoSobreOprFinanceirasTituloCobranca\":0,\"valorImpostoSobreOprFinanceirasTituloCobranca\":0,\"valorMoedaTituloCobranca\":0,\"percentualJuroMoraTitulo\":0,\"valorJuroMoraTitulo\":0,\"percentualMultaTitulo\":0,\"valorMultaTituloCobranca\":0,\"quantidadeParcelaTituloCobranca\":0,\"dataBaixaAutomaticoTitulo\":\"14.04.2024\",\"textoCampoUtilizacaoCedente\":\"\",\"indicadorCobrancaPartilhadoTitulo\":\"N\",\"nomeSacadoCobranca\":\"TECIDOS FARIA DUARTE\",\"textoEnderecoSacadoCobranca\":\"Avenida Dias Gomes 1970\",\"nomeBairroSacadoCobranca\":\"Centro\",\"nomeMunicipioSacadoCobranca\":\"Sucupira\",\"siglaUnidadeFederacaoSacadoCobranca\":\"TO\",\"numeroCepSacadoCobranca\":77458000,\"valorMoedaAbatimentoTitulo\":0,\"dataProtestoTituloCobranca\":\"\",\"codigoTipoInscricaoSacador\":3,\"numeroInscricaoSacadorAvalista\":0,\"nomeSacadorAvalistaTitulo\":\"\",\"percentualDescontoTitulo\":0,\"dataDescontoTitulo\":\"\",\"valorDescontoTitulo\":0,\"codigoDescontoTitulo\":0,\"percentualSegundoDescontoTitulo\":0,\"dataSegundoDescontoTitulo\":\"\",\"valorSegundoDescontoTitulo\":0,\"codigoSegundoDescontoTitulo\":0,\"percentualTerceiroDescontoTitulo\":0,\"dataTerceiroDescontoTitulo\":\"\",\"valorTerceiroDescontoTitulo\":0,\"codigoTerceiroDescontoTitulo\":0,\"dataMultaTitulo\":\"\",\"numeroCarteiraCobranca\":17,\"numeroVariacaoCarteiraCobranca\":35,\"quantidadeDiaProtesto\":0,\"quantidadeDiaPrazoLimiteRecebimento\":360,\"dataLimiteRecebimentoTitulo\":\"09.04.2024\",\"indicadorPermissaoRecebimentoParcial\":\"S\",\"textoCodigoBarrasTituloCobranca\":\"00191932100000012340000003128557300000000317\",\"codigoOcorrenciaCartorio\":0,\"valorImpostoSobreOprFinanceirasRecebidoTitulo\":0,\"valorAbatimentoTotal\":0,\"valorJuroMoraRecebido\":0,\"valorDescontoUtilizado\":0,\"valorPagoSacado\":0,\"valorCreditoCedente\":0,\"codigoTipoLiquidacao\":0,\"dataCreditoLiquidacao\":\"\",\"dataRecebimentoTitulo\":\"\",\"codigoPrefixoDependenciaRecebedor\":0,\"codigoNaturezaRecebimento\":0,\"numeroIdentidadeSacadoTituloCobranca\":\"\",\"codigoResponsavelAtualizacao\":\"\",\"codigoTipoBaixaTitulo\":0,\"valorMultaRecebido\":0,\"valorReajuste\":0,\"valorOutroRecebido\":0,\"codigoIndicadorEconomicoUtilizadoInadimplencia\":0}";

        // when
        final var actualResponse = cobrancaBoletoFeignClient.findByNossoNumeroAndConvenio(
                authorization,
                expectedDevAppKey,
                expectedNossoNumero,
                expectedNumeroConvenio
        );

        // then
        Assertions.assertNotNull(actualResponse);
        final var actualUrl = actualResponse.request().url();
        Assertions.assertEquals(expectedUrl, actualUrl);
        final var actualResponseBody = StreamUtils.copyToString(
                actualResponse.body().asInputStream(),
                StandardCharsets.UTF_8
        );
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    void givenInvalidNossoNumero_whenCallsFindByNossoNumeroAndConvenio_shouldReturnResponse() throws IOException {
        // given
        final var token = oAuthClientService.generateToken();
        final var authorization = token.getBearerToken();
        final var expectedNossoNumero = "00041285573100020000";
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNumeroConvenio = 3128557;
        final var expectedStatus = 400;
        final var expectedReason = "Bad Request";
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos/00041285573100020000?gw-dev-app-key=daae06a486398c4dddca2c6ffcbdab1a&numeroConvenio=3128557";
        final var expectedResponseBody = "{\"errors\":[{\"code\":\"4678420.1\",\"message\":\"Campo nosso número preenchido com dados inválidos.\"}]}";

        // when
        final var actualResponse = cobrancaBoletoFeignClient.findByNossoNumeroAndConvenio(
                authorization,
                expectedDevAppKey,
                expectedNossoNumero,
                expectedNumeroConvenio
        );

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(expectedStatus, actualResponse.status());
        Assertions.assertEquals(expectedReason, actualResponse.reason());
        final var actualUrl = actualResponse.request().url();
        Assertions.assertEquals(expectedUrl, actualUrl);
        final var actualResponseBody = StreamUtils.copyToString(
                actualResponse.body().asInputStream(),
                StandardCharsets.UTF_8
        );
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    void givenAValidNossoNumeroAndConvenio_whenCallsCreatePixBoleto_shouldReturnResponse() throws IOException {
        // given
        final var token = oAuthClientService.generateToken();
        final var bearerToken = token.getBearerToken();
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNossoNumero = "00031285573000000029"; // precisa registrar um novo boleto antes de executar o test
        final var expectedNumeroConvenio = 3128557;
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos/%s/gerar-pix?gw-dev-app-key=%s"
                .formatted(expectedNossoNumero, expectedDevAppKey);
        final var expectedRequestBody = "{\"numeroConvenio\":3128557}";

        final var request = new GeraPixBoletoRequest(expectedNumeroConvenio);

        // when
        final var actualResponse = cobrancaBoletoFeignClient.createPixBoleto(
                bearerToken,
                expectedDevAppKey,
                expectedNossoNumero,
                request
        );

        // then
        Assertions.assertNotNull(actualResponse);
        final var actualRequest = actualResponse.request();
        final var actualUrl = actualRequest.url();
        Assertions.assertEquals(expectedUrl, actualUrl);
        final var actualRequestBody = new String(actualRequest.body(), StandardCharsets.UTF_8);
        Assertions.assertEquals(expectedRequestBody, actualRequestBody);
        final var actualResponseBody = StreamUtils.copyToString(
                actualResponse.body().asInputStream(),
                StandardCharsets.UTF_8
        );
        Assertions.assertTrue(actualResponseBody.contains("chave"));
        Assertions.assertTrue(actualResponseBody.contains("url"));
        Assertions.assertTrue(actualResponseBody.contains("txId"));
        Assertions.assertTrue(actualResponseBody.contains("emv"));
    }

    @Test
    void givenAnInvalidNossoNumeroAndConvenio_whenCallsCreatePixBoleto_shouldReturnResponse() throws IOException {
        // given
        final var token = oAuthClientService.generateToken();
        final var bearerToken = token.getBearerToken();
        final var expectedDevAppKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var expectedNossoNumero = "00031285573000000029"; // precisa registrar um novo boleto antes de executar o test
        final var expectedNumeroConvenio = 3128557;
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos/%s/gerar-pix?gw-dev-app-key=%s"
                .formatted(expectedNossoNumero, expectedDevAppKey);
        final var expectedRequestBody = "{\"numeroConvenio\":3128557}";
        final var expectedHttpStatus = 400;

        final var request = new GeraPixBoletoRequest(expectedNumeroConvenio);

        // when
        final var actualResponse = cobrancaBoletoFeignClient.createPixBoleto(
                bearerToken,
                expectedDevAppKey,
                expectedNossoNumero,
                request
        );

        // then
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(expectedHttpStatus, actualResponse.status());
        final var actualRequest = actualResponse.request();
        final var actualUrl = actualRequest.url();
        Assertions.assertEquals(expectedUrl, actualUrl);
        final var actualRequestBody = new String(actualRequest.body(), StandardCharsets.UTF_8);
        Assertions.assertEquals(expectedRequestBody, actualRequestBody);
        final var actualResponseBody = StreamUtils.copyToString(
                actualResponse.body().asInputStream(),
                StandardCharsets.UTF_8
        );
        Assertions.assertFalse(actualResponseBody.contains("chave"));
        Assertions.assertFalse(actualResponseBody.contains("url"));
        Assertions.assertFalse(actualResponseBody.contains("txId"));
        Assertions.assertFalse(actualResponseBody.contains("emv"));
    }
}