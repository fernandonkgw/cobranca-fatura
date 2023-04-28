package com.fnaka.cobrancafatura.application.boleto.criapix;

import com.fnaka.cobrancafatura.UseCaseTest;
import com.fnaka.cobrancafatura.domain.boleto.*;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CriaPixBoletoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCriaPixBoletoUseCase useCase;
    @Mock
    private BoletoGateway boletoGateway;
    @Mock
    private CobrancaGateway cobrancaGateway;

    @Test
    void givenAValidCommand_whenCallsCriaPix_shouldReturnPix() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";
        final var expectedUrl = "qrcodepix-h.bb.com.br/pix/v2/cobv/e09b4025-1598-4688-acb6-0fe20fe61456";
        final var expectedTxId = "BOLETO31285573000000009DATA27042023";
        final var expectedEmv = "00020101021226920014br.gov.bcb.pix2570qrcodepix-h.bb.com.br/pix/v2/cobv/e09b4025-1598-4688-acb6-0fe20fe61456520400005303986540512.345802BR5919PADARIA PESSOA ROSA6008BRASILIA62070503***6304A705";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();
        boleto.confirmaRegistro();
        final var pix = new PixBoleto(
                expectedUrl,
                expectedTxId,
                expectedEmv
        );

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        when(cobrancaGateway.createPix(expectedConvenio, expectedNossoNumero))
                .thenReturn(pix);

        when(boletoGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedUrl, actualOutput.url());
        Assertions.assertEquals(expectedTxId, actualOutput.txId());
        Assertions.assertEquals(expectedEmv, actualOutput.emv());
    }

    @Test
    void givenAnInvalidId_whenCallsCriaPix_shouldThrowsException() {
        // given
        final var expectedId = BoletoID.from("invalid-id");
        final var expectedErrorMessage = "'boleto' not found";
        final var expectedErrorCode = ErrorCode.CFA_006;

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.empty());

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
    }

    @Test
    void givenAnInvalidStatus_whenCallsCriaPix_shouldReturnPix() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();
        final var expectedErrorMessage = "'status' must be REGISTRADO to generate Pix";
        final var expectedErrorCode = ErrorCode.CFA_007;

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
    }

    @Override
    protected List<Object> getMocks() {
        return List.of(boletoGateway, cobrancaGateway);
    }
}