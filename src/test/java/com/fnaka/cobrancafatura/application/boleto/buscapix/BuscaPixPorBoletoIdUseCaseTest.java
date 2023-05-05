package com.fnaka.cobrancafatura.application.boleto.buscapix;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BuscaPixPorBoletoIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultBuscaPixPorBoletoIdUseCase useCase;

    @Mock
    private BoletoGateway boletoGateway;

    @Test
    void givenABoletoWithStatusPixCriado_whenCallsBuscaPixPorBoletoId_shouldReturnOutput() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";
        final var expectedStatus = BoletoStatus.PIX_CRIADO;
        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();
        final var expectedUrl = "qrcodepix-h.bb.com.br/pix/v2/cobv/299a3f47-7f18-44ec-9711-bfaaa4658da7";
        final var expectedTxId = "BOLETO31285573200000046DATA04052023";
        final var expectedEmv = "00020101021226920014br.gov.bcb.pix2570qrcodepix-h.bb.com.br/pix/v2/cobv/299a3f47-7f18-44ec-9711-bfaaa4658da7520400005303986540512.345802BR5919PADARIA PESSOA ROSA6008BRASILIA62070503***6304A171";
        final var pixBoleto = new PixBoleto(expectedUrl, expectedTxId, expectedEmv);
        boleto.criaPix(pixBoleto);
        Assertions.assertEquals(expectedStatus, boleto.getStatus());

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        // when
        final var actualOutput = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedUrl, actualOutput.url());
        Assertions.assertEquals(expectedTxId, actualOutput.txId());
        Assertions.assertEquals(expectedEmv, actualOutput.emv());

        verify(boletoGateway).findById(eq(expectedId));
    }

    @Test
    void givenAnInvalidId_whenCallsBuscaPixPorBoletoId_shouldThrowsDomainException() {
        // given
        final var expectedId = BoletoID.from("invalid-id");
        final var expectedErrorCode = ErrorCode.CFA_006;
        final var expectedErrorMessage = "'boleto' was not found";

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.empty());

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
    }

    @Test
    void givenAnInvalidStatusCriado_whenCallsBuscaPixPorBoletoId_shouldThrowsDomainException() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";
        final var expectedStatus = BoletoStatus.CRIADO;
        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();
        final var expectedErrorCode = ErrorCode.CFA_010;
        final var expectedErrorMessage = "'status' must be PIX_CRIADO";
        Assertions.assertEquals(expectedStatus, boleto.getStatus());

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
    }

    @Override
    protected List<Object> getMocks() {
        return List.of(boletoGateway);
    }
}