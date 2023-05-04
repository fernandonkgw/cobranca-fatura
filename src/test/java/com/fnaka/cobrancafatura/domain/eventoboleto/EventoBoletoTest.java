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
        Assertions.assertEquals(expectedBoletoId, actualEventoBoleto.getBoletoId());
        Assertions.assertEquals(expectedStatus, actualEventoBoleto.getStatus());
        Assertions.assertNotNull(actualEventoBoleto.getCriadoEm());
        Assertions.assertNull(actualEventoBoleto.getRequisicao());
    }
}