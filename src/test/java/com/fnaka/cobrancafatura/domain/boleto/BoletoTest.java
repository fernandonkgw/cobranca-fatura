package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoletoTest {

    @Test
    void givenValidParams_whenCallsNewBoleto_shouldInstantiateABoleto() {
        // given
        final var expectedConvenio = 1;
        final var expectedNumeroTituloCliente = "0001";
        final var expectedStatus = BoletoStatus.CRIADO;

        // when
        final var actualBoleto = Boleto.newBoleto(expectedConvenio, expectedNumeroTituloCliente);

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertNotNull(actualBoleto.getId());
        Assertions.assertEquals(expectedConvenio, actualBoleto.getConvenio());
        Assertions.assertEquals(expectedNumeroTituloCliente, actualBoleto.getNumeroTituloCliente());
        Assertions.assertEquals(expectedStatus, actualBoleto.getStatus());
        Assertions.assertNotNull(actualBoleto.getCriadoEm());
        Assertions.assertNotNull(actualBoleto.getAtualizadoEm());
        Assertions.assertEquals(actualBoleto.getCriadoEm(), actualBoleto.getAtualizadoEm());
    }

    @Test
    void givenAnInvalidNullConvenio_shouldCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedNumeroTituloCliente = "0001";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'convenio' should not be null";
        final var expectedErrorCode = ErrorCode.CFA_001;

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(null, expectedNumeroTituloCliente)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
    }

    @Test
    void givenAnInvalidConvenioLessThanZero_shouldCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedConvenio = -1;
        final var expectedNumeroTituloCliente = "0001";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'convenio' should not be less than zero";
        final var expectedErrorCode = ErrorCode.CFA_002;
        final var expectedErrorParam = "-1";

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(expectedConvenio, expectedNumeroTituloCliente)
        );

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
        Assertions.assertEquals(expectedErrorParam, actualException.getFirstError().getFirstParam());
    }

    @Test
    void givenAnInvalidNullNumeroTituloCliente_whenCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedConvenio = 1;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'numeroTituloCliente' should not be null";
        final var expectedErrorCode = ErrorCode.CFA_003;

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(expectedConvenio, null)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
    }
}