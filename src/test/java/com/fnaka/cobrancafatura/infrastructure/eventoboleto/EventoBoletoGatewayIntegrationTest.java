package com.fnaka.cobrancafatura.infrastructure.eventoboleto;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence.EventoBoletoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class EventoBoletoGatewayIntegrationTest {

    @Autowired
    private DefaultEventoBoletoGateway eventoBoletoGateway;

    @Autowired
    private EventoBoletoRepository eventoBoletoRepository;

    @Test
    void testDependencies() {
        Assertions.assertNotNull(eventoBoletoGateway);
        Assertions.assertNotNull(eventoBoletoRepository);
    }

    @Test
    void givenAValidEventoBoleto_whenCallsCreate_shouldPersist() {
        // given
        final var expectedNossoNumero = "00031285573000000014";
        final var expectedConvenio = 3128557;
        final var expectedStatus = BoletoStatus.CRIADO;

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedBoletoId = boleto.getId();
        final var eventoBoleto = boleto.newEvento();
        final var expectedId = eventoBoleto.getId();
        eventoBoleto.concluido(boleto);

        // when
        final var actualEventoBoleto = eventoBoletoGateway.create(eventoBoleto);

        // then
        Assertions.assertNotNull(actualEventoBoleto);
        Assertions.assertEquals(expectedId, actualEventoBoleto.getId());
        Assertions.assertEquals(expectedBoletoId, actualEventoBoleto.getBoletoId());
        Assertions.assertEquals(expectedStatus, actualEventoBoleto.getStatus());
        Assertions.assertEquals(eventoBoleto.getCriadoEm(), actualEventoBoleto.getCriadoEm());

        final var persistedEvento = eventoBoletoRepository.findById(expectedId.getValue()).get();
        Assertions.assertEquals(expectedId.getValue(), persistedEvento.getId());
        Assertions.assertEquals(expectedBoletoId.getValue(), persistedEvento.getBoletoId());
        Assertions.assertEquals(expectedStatus, persistedEvento.getStatus());
        Assertions.assertEquals(actualEventoBoleto.getCriadoEm(), persistedEvento.getCriadoEm());
        Assertions.assertNull(persistedEvento.getUrlRequisicao());
        Assertions.assertNull(persistedEvento.getPayloadRequest());
        Assertions.assertNull(persistedEvento.getPayloadResponse());
    }
}