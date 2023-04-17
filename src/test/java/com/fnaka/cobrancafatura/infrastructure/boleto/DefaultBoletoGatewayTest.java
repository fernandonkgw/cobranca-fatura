package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class DefaultBoletoGatewayTest {

    @Autowired
    private DefaultBoletoGateway boletoGateway;

    @Autowired
    private BoletoRepository boletoRepository;

    @Test
    void testDependencies() {
        Assertions.assertNotNull(boletoGateway);
        Assertions.assertNotNull(boletoRepository);
    }

    @Test
    void givenAValidBoleto_whenCallsCreate_shouldPersistIt() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNumeroTituloCliente = "12345678901234567890";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNumeroTituloCliente);
        final var expectedId = boleto.getId();

        // when
        final var actualBoleto = boletoGateway.create(boleto);

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertEquals(expectedConvenio, actualBoleto.getConvenio());
        Assertions.assertEquals(expectedNumeroTituloCliente, actualBoleto.getNumeroTituloCliente());

        final var persistedBoleto = boletoRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persistedBoleto.getId());
        Assertions.assertEquals(expectedConvenio, persistedBoleto.getConvenio());
        Assertions.assertEquals(expectedNumeroTituloCliente, persistedBoleto.getNumeroTituloCliente());
        Assertions.assertEquals(boleto.getStatus(), persistedBoleto.getStatus());
        Assertions.assertEquals(boleto.getCriadoEm(), persistedBoleto.getCriadoEm());
        Assertions.assertEquals(boleto.getAtualizadoEm(), persistedBoleto.getAtualizadoEm());

    }
}
