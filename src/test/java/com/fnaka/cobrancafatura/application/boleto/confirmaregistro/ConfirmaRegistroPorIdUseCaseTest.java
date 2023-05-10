package com.fnaka.cobrancafatura.application.boleto.confirmaregistro;

import com.fnaka.cobrancafatura.UseCaseTest;
import com.fnaka.cobrancafatura.domain.boleto.*;
import com.fnaka.cobrancafatura.domain.dtos.CobrancaBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.domain.eventoboleto.Requisicao;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfirmaRegistroPorIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultConfirmaRegistroPorIdUseCase useCase;

    @Mock
    private BoletoGateway boletoGateway;
    @Mock
    private CobrancaBoletoGateway cobrancaBoletoGateway;

    @Mock
    private EventoBoletoGateway eventoBoletoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(boletoGateway);
    }

    @Test
    void givenAValidCommand_whenCallsConfirmaRegistro_shouldUpdateStatusRegistrado() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);

        final var cobrancaBoleto = new CobrancaBoleto("123", 1, 1, 10);
        final var requisicao = new Requisicao("http://", null, "abc", 100L);
        final var cobrancaBoletoRequisicao = new CobrancaBoletoRequisicao(cobrancaBoleto, requisicao);

        final var expectedId = boleto.getId();
        final var expectedStatus = BoletoStatus.REGISTRADO;

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        when(cobrancaBoletoGateway.findByNossoNumeroAndConvenio(anyString(), anyInt()))
                .thenReturn(cobrancaBoletoRequisicao);

        // when
        useCase.execute(expectedId.getValue());

        // then
        verify(boletoGateway).findById(eq(expectedId));
        verify(cobrancaBoletoGateway)
                .findByNossoNumeroAndConvenio(
                        eq(expectedNossoNumero), eq(expectedConvenio)
                );
        final var captor = ArgumentCaptor.forClass(Boleto.class);
        verify(boletoGateway).update(captor.capture());
        final var boletoUpdated = captor.getValue();
        Assertions.assertNotNull(boletoUpdated);
        Assertions.assertEquals(expectedId, boletoUpdated.getId());
        Assertions.assertEquals(expectedConvenio, boletoUpdated.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, boletoUpdated.getNossoNumero());
        Assertions.assertEquals(expectedStatus, boletoUpdated.getStatus());
        Assertions.assertEquals(boleto.getCriadoEm(), boletoUpdated.getCriadoEm());
    }

    @Test
    void givenAnInvalidBoletoId_whenCallsConfirmaRegistro_shouldThrowsDomainException() {
        // given
        final var invalidId = BoletoID.from("invalid-id");
        final var expectedErrorMessage = "'boleto' was not found";
        final var expectedErrorCode = ErrorCode.CFA_006;

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.empty());

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(invalidId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
    }

    @Test
    void givenCobrancaNaoRegistrada_whenCallsConfirmaRegistro_shouldUpdateStatusNaoRegistrado() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);

        final var requisicao = new Requisicao("http://", null, "abc", 100L);
        final var cobrancaBoletoRequisicao = new CobrancaBoletoRequisicao(null, requisicao);

        final var expectedId = boleto.getId();
        final var expectedStatus = BoletoStatus.NAO_REGISTRADO;

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        when(cobrancaBoletoGateway.findByNossoNumeroAndConvenio(any(), any()))
                .thenReturn(cobrancaBoletoRequisicao);

        // when
        useCase.execute(expectedId.getValue());

        // then
        verify(boletoGateway).findById(eq(expectedId));
        verify(cobrancaBoletoGateway)
                .findByNossoNumeroAndConvenio(
                        eq(expectedNossoNumero), eq(expectedConvenio)
                );
        final var captor = ArgumentCaptor.forClass(Boleto.class);
        verify(boletoGateway).update(captor.capture());
        final var boletoUpdated = captor.getValue();
        Assertions.assertNotNull(boletoUpdated);
        Assertions.assertEquals(expectedId, boletoUpdated.getId());
        Assertions.assertEquals(expectedConvenio, boletoUpdated.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, boletoUpdated.getNossoNumero());
        Assertions.assertEquals(expectedStatus, boletoUpdated.getStatus());
        Assertions.assertEquals(boleto.getCriadoEm(), boletoUpdated.getCriadoEm());
    }
}