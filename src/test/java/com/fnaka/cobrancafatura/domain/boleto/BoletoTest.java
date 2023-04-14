package com.fnaka.cobrancafatura.domain.boleto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoletoTest {

    @Test
    void givenValidParams_whenCallsNewBoleto_shouldInstatiateABoleto() {
        // given
        final var expectedConvenio = 1;
        final var expectedNumeroTituloCliente = "0001";

        // when
        final var actualBoleto = Boleto.newBoleto(expectedConvenio, expectedNumeroTituloCliente);

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertNotNull(actualBoleto.getId());
        Assertions.assertEquals(expectedConvenio, actualBoleto.getConvenio());
        Assertions.assertEquals(expectedNumeroTituloCliente, actualBoleto.getNumeroTituloCliente());
        Assertions.assertNotNull(actualBoleto.getCriadoEm());
        Assertions.assertNotNull(actualBoleto.getAtualizadoEm());
        Assertions.assertEquals(actualBoleto.getCriadoEm(), actualBoleto.getAtualizadoEm());
    }

    @Test
    void givenAnInvalidNullConvenio_shouldCallsNewBoleto_shouldReceiveNotification() {
        // given
        final Integer expectedConvenio = null;
        final var expectedNumeroTituloCliente = "0001";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'convenio' should not be null";
        final var expectedErrorCode = "CFA-001";

//        final var actualException = Assertions.assertThrows(
//                NotificationException.class,
//                () -> Boleto.newBoleto(expectedConvenio, expectedNumeroTituloCliente)
//        );

    }
}