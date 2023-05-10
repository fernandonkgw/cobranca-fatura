package com.fnaka.cobrancafatura.domain.eventoboleto;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EventoBoletoTest {

    @Test
    void givenAValidParams_whenCallsNewEvento_shouldInstantiateIt() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedStatus = BoletoStatus.CRIADO;
        final var expectedBoleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedBoletoId = expectedBoleto.getId();

        // when
        final var actualEventoBoleto = EventoBoleto.newEvento(expectedBoleto);

        // then
        Assertions.assertNotNull(actualEventoBoleto);
        Assertions.assertNotNull(actualEventoBoleto.getId());
        Assertions.assertEquals(expectedBoletoId, actualEventoBoleto.getBoleto().getId());
        Assertions.assertEquals(expectedStatus, actualEventoBoleto.getStatus());
        Assertions.assertNotNull(actualEventoBoleto.getCriadoEm());
        Assertions.assertNull(actualEventoBoleto.getRequisicao());
    }

    @Test
    void givenAValidParamsWithRequisicao_whenCallsNewEvento_shouldInstantiateIt() {
        // given
        final var expectedConvenio = 3128557;
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedStatus = BoletoStatus.CRIADO;
        final var expectedBoleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedBoletoId = expectedBoleto.getId();
        final var expectedUrl = "https://api.sandbox.bb.com.br/cobrancas/v2/boletos";
        final var expectedPayloadRequest = "{\"numeroConvenio\":3128557}";
        final var expectedPayloadResponse = "{\"codigoLinhaDigitavel\":\"00190000090312855730700000003178193210000001234\",\"textoEmailPagador\":\"\",\"textoMensagemBloquetoTitulo\":\"\",\"codigoTipoMulta\":0,\"codigoCanalPagamento\":0,\"numeroContratoCobranca\":19581316,\"codigoTipoInscricaoSacado\":2,\"numeroInscricaoSacadoCobranca\":74910037000193,\"codigoEstadoTituloCobranca\":1,\"codigoTipoTituloCobranca\":2,\"codigoModalidadeTitulo\":1,\"codigoAceiteTituloCobranca\":\"A\",\"codigoPrefixoDependenciaCobrador\":794,\"codigoIndicadorEconomico\":9,\"numeroTituloCedenteCobranca\":\"\",\"codigoTipoJuroMora\":0,\"dataEmissaoTituloCobranca\":\"01.04.2023\",\"dataRegistroTituloCobranca\":\"03.04.2023\",\"dataVencimentoTituloCobranca\":\"15.04.2023\",\"valorOriginalTituloCobranca\":12.34,\"valorAtualTituloCobranca\":12.34,\"valorPagamentoParcialTitulo\":0,\"valorAbatimentoTituloCobranca\":0,\"percentualImpostoSobreOprFinanceirasTituloCobranca\":0,\"valorImpostoSobreOprFinanceirasTituloCobranca\":0,\"valorMoedaTituloCobranca\":0,\"percentualJuroMoraTitulo\":0,\"valorJuroMoraTitulo\":0,\"percentualMultaTitulo\":0,\"valorMultaTituloCobranca\":0,\"quantidadeParcelaTituloCobranca\":0,\"dataBaixaAutomaticoTitulo\":\"14.04.2024\",\"textoCampoUtilizacaoCedente\":\"\",\"indicadorCobrancaPartilhadoTitulo\":\"N\",\"nomeSacadoCobranca\":\"TECIDOS FARIA DUARTE\",\"textoEnderecoSacadoCobranca\":\"Avenida Dias Gomes 1970\",\"nomeBairroSacadoCobranca\":\"Centro\",\"nomeMunicipioSacadoCobranca\":\"Sucupira\",\"siglaUnidadeFederacaoSacadoCobranca\":\"TO\",\"numeroCepSacadoCobranca\":77458000,\"valorMoedaAbatimentoTitulo\":0,\"dataProtestoTituloCobranca\":\"\",\"codigoTipoInscricaoSacador\":3,\"numeroInscricaoSacadorAvalista\":0,\"nomeSacadorAvalistaTitulo\":\"\",\"percentualDescontoTitulo\":0,\"dataDescontoTitulo\":\"\",\"valorDescontoTitulo\":0,\"codigoDescontoTitulo\":0,\"percentualSegundoDescontoTitulo\":0,\"dataSegundoDescontoTitulo\":\"\",\"valorSegundoDescontoTitulo\":0,\"codigoSegundoDescontoTitulo\":0,\"percentualTerceiroDescontoTitulo\":0,\"dataTerceiroDescontoTitulo\":\"\",\"valorTerceiroDescontoTitulo\":0,\"codigoTerceiroDescontoTitulo\":0,\"dataMultaTitulo\":\"\",\"numeroCarteiraCobranca\":17,\"numeroVariacaoCarteiraCobranca\":35,\"quantidadeDiaProtesto\":0,\"quantidadeDiaPrazoLimiteRecebimento\":360,\"dataLimiteRecebimentoTitulo\":\"09.04.2024\",\"indicadorPermissaoRecebimentoParcial\":\"S\",\"textoCodigoBarrasTituloCobranca\":\"00191932100000012340000003128557300000000317\",\"codigoOcorrenciaCartorio\":0,\"valorImpostoSobreOprFinanceirasRecebidoTitulo\":0,\"valorAbatimentoTotal\":0,\"valorJuroMoraRecebido\":0,\"valorDescontoUtilizado\":0,\"valorPagoSacado\":0,\"valorCreditoCedente\":0,\"codigoTipoLiquidacao\":0,\"dataCreditoLiquidacao\":\"\",\"dataRecebimentoTitulo\":\"\",\"codigoPrefixoDependenciaRecebedor\":0,\"codigoNaturezaRecebimento\":0,\"numeroIdentidadeSacadoTituloCobranca\":\"\",\"codigoResponsavelAtualizacao\":\"\",\"codigoTipoBaixaTitulo\":0,\"valorMultaRecebido\":0,\"valorReajuste\":0,\"valorOutroRecebido\":0,\"codigoIndicadorEconomicoUtilizadoInadimplencia\":0}";
        final var expectedResponseTime = 1000L;
        final var expectedRequisicao = new Requisicao(
                expectedUrl,
                expectedPayloadRequest,
                expectedPayloadResponse,
                expectedResponseTime
        );

        // when
        final var actualEventoBoleto = EventoBoleto.newEvento(expectedBoleto, expectedRequisicao);

        // then
        Assertions.assertNotNull(actualEventoBoleto);
        Assertions.assertNotNull(actualEventoBoleto.getId());
        Assertions.assertEquals(expectedBoletoId, actualEventoBoleto.getBoleto().getId());
        Assertions.assertEquals(expectedStatus, actualEventoBoleto.getStatus());
        final var actualRequisicao = actualEventoBoleto.getRequisicao();
        Assertions.assertEquals(expectedUrl, actualRequisicao.url());
        Assertions.assertEquals(expectedPayloadRequest, actualRequisicao.payloadRequest());
        Assertions.assertEquals(expectedPayloadResponse, actualRequisicao.payloadResponse());
        Assertions.assertEquals(expectedResponseTime, actualRequisicao.responseTime());

    }
}