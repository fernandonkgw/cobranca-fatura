package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
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
        final var expectedNossoNumero = "12345678901234567890";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();

        // when
        final var actualBoleto = boletoGateway.create(boleto);

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertEquals(expectedConvenio, actualBoleto.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, actualBoleto.getNossoNumero());

        final var persistedBoleto = boletoRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persistedBoleto.getId());
        Assertions.assertEquals(expectedConvenio, persistedBoleto.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, persistedBoleto.getNossoNumero());
        Assertions.assertEquals(boleto.getStatus(), persistedBoleto.getStatus());
        Assertions.assertEquals(boleto.getCriadoEm(), persistedBoleto.getCriadoEm());
        Assertions.assertEquals(boleto.getAtualizadoEm(), persistedBoleto.getAtualizadoEm());

    }

    @Test
    void givenAValidBoletoRegistrado_whenCallsUpdate_shouldUpdateIt() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";
        final var exoectedStatus = BoletoStatus.REGISTRADO;

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();

        boleto.confirmaRegistro();

        // when
        final var actualBoleto = boletoGateway.update(boleto);

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertEquals(expectedConvenio, actualBoleto.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, actualBoleto.getNossoNumero());
        Assertions.assertEquals(exoectedStatus, actualBoleto.getStatus());

        final var persistedBoleto = boletoRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persistedBoleto.getId());
        Assertions.assertEquals(expectedConvenio, persistedBoleto.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, persistedBoleto.getNossoNumero());
        Assertions.assertEquals(boleto.getStatus(), persistedBoleto.getStatus());
        Assertions.assertEquals(boleto.getCriadoEm(), persistedBoleto.getCriadoEm());
        Assertions.assertEquals(boleto.getAtualizadoEm(), persistedBoleto.getAtualizadoEm());

    }
}
