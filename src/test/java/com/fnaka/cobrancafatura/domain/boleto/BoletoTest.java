package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoletoTest {

    @Test
    void givenValidParams_whenCallsNewBoleto_shouldInstantiateABoleto() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedStatus = BoletoStatus.CRIADO;

        // when
        final var actualBoleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertNotNull(actualBoleto.getId());
        Assertions.assertEquals(expectedConvenio, actualBoleto.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, actualBoleto.getNossoNumero());
        Assertions.assertEquals(expectedStatus, actualBoleto.getStatus());
        Assertions.assertNotNull(actualBoleto.getCriadoEm());
        Assertions.assertNotNull(actualBoleto.getAtualizadoEm());
        Assertions.assertEquals(actualBoleto.getCriadoEm(), actualBoleto.getAtualizadoEm());
    }

    @Test
    void givenAnInvalidNullConvenio_shouldCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'convenio' should not be null";
        final var expectedErrorCode = ErrorCode.CFA_001;

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(null, expectedNossoNumero)
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
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'convenio' should not be less than zero";
        final var expectedErrorCode = ErrorCode.CFA_002;
        final var expectedErrorParam = "-1";

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(expectedConvenio, expectedNossoNumero)
        );

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
        Assertions.assertEquals(expectedErrorParam, actualException.getFirstError().getFirstParam());
    }

    @Test
    void givenAnInvalidLengthConvenio_shouldCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedConvenio = 12345678;
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'convenio' must be 7 characters";
        final var expectedErrorCode = ErrorCode.CFA_005;
        final var expectedErrorParam = "12345678";

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(expectedConvenio, expectedNossoNumero)
        );

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
        Assertions.assertEquals(expectedErrorParam, actualException.getFirstError().getFirstParam());
    }

    @Test
    void givenAnInvalidNullNossoNumero_whenCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nossoNumero' should not be null";
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

    @Test
    void givenAnInvalidEmptyNossoNumero_whenCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "   ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nossoNumero' should not be empty";
        final var expectedErrorCode = ErrorCode.CFA_004;

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(expectedConvenio, expectedNossoNumero)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
    }

    @Test
    void givenAnInvalidLengthNossoNumero_shouldCallsNewBoleto_shouldThrowsDomainException() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "000312855730000000080";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nossoNumero' must be 20 characters";
        final var expectedErrorCode = ErrorCode.CFA_008;
        final var expectedErrorParam = "000312855730000000080";

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> Boleto.newBoleto(expectedConvenio, expectedNossoNumero)
        );

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
        Assertions.assertEquals(expectedErrorParam, actualException.getFirstError().getFirstParam());
    }

    @Test
    void givenValidParams_whenCallsRegistroConfirmado_shouldUpdateStatus() throws InterruptedException {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedStatus = BoletoStatus.REGISTRADO;

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();
        final var beforeUpdate = boleto.getAtualizadoEm();
        Thread.sleep(100);

        // when
        final var actualBoleto = boleto.registroConfirmado();

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertNotNull(actualBoleto.getId());
        Assertions.assertEquals(expectedStatus, actualBoleto.getStatus());
        Assertions.assertNotNull(beforeUpdate.isBefore(actualBoleto.getAtualizadoEm()));
        Assertions.assertTrue(actualBoleto.isRegistrado());
        final var event = (BoletoRegistradoEvent) actualBoleto.getDomainEvent();
        Assertions.assertEquals(expectedId.getValue(), event.id());
        Assertions.assertEquals(expectedStatus, event.status());
    }

    @Test
    void givenValidParams_whenCallsRegistroNaoEncontrado_shouldUpdateStatus() throws InterruptedException {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "00031285573000000008";
        final var expectedStatus = BoletoStatus.NAO_REGISTRADO;

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();
        final var beforeUpdate = boleto.getAtualizadoEm();
        Thread.sleep(100);

        // when
        final var actualBoleto = boleto.registroNaoEncontrado();

        // then
        Assertions.assertNotNull(actualBoleto);
        Assertions.assertNotNull(actualBoleto.getId());
        Assertions.assertEquals(expectedStatus, actualBoleto.getStatus());
        Assertions.assertNotNull(beforeUpdate.isBefore(actualBoleto.getAtualizadoEm()));
        Assertions.assertTrue(actualBoleto.isNaoRegistrado());
        final var event = (BoletoNaoRegistradoEvent) actualBoleto.getDomainEvent();
        Assertions.assertEquals(expectedId.getValue(), event.id());
        Assertions.assertEquals(expectedStatus, event.status());
    }
}