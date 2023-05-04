package com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence;

import com.fnaka.cobrancafatura.PostgreSQLGatewayTest;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PostgreSQLGatewayTest
class EventoBoletoRepositoryIntegrationTest {

    @Autowired
    private BoletoRepository boletoRepository;

    @Autowired
    private EventoBoletoRepository eventoBoletoRepository;

    @Test
    void givenPersistedEvento_whenCallsFindByBoletoId_shouldReturnList() {
        // given
        final var expectedNossoNumero = "00031285571000000033";
        final var expectedConvenio = 3128557;
        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedBoletoId = boleto.getId();
        final var expectedEventoBoletoCount = 1;

        boletoRepository.saveAndFlush(BoletoJpaEntity.from(boleto));

        final var evento = boleto.newEvento();
        eventoBoletoRepository.saveAndFlush(EventoBoletoJpaEntity.from(evento));

        Assertions.assertEquals(1, eventoBoletoRepository.count());

        // when
        final var actualList = eventoBoletoRepository.findByBoletoId(expectedBoletoId.getValue());

        // then
        Assertions.assertNotNull(actualList);
        Assertions.assertFalse(actualList.isEmpty());
        Assertions.assertEquals(expectedEventoBoletoCount, actualList.size());
    }
}