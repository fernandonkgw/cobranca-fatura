package com.fnaka.cobrancafatura.application.boleto.confirmaregistro;

import com.fnaka.cobrancafatura.UseCaseTest;
import com.fnaka.cobrancafatura.domain.boleto.*;
import com.fnaka.cobrancafatura.domain.events.DomainEventPublisher;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfirmaRegistroPorIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultConfirmaRegistroPorIdUseCase useCase;

    @Mock
    private BoletoGateway boletoGateway;
    @Mock
    private CobrancaGateway cobrancaGateway;
    @Mock
    private DomainEventPublisher publisher;

    @Override
    protected List<Object> getMocks() {
        return List.of(boletoGateway);
    }

    @Test
    void givenAValidCommand_whenCallsConfirmaRegistro_shouldUpdateStatusRegistrado() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNumeroTituloCliente = "12345678901234567890";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNumeroTituloCliente);
        boleto.publishDomainEvent(publisher);

        final var cobranca = new Cobranca("123", 1, 1, 10);

        final var expectedId = boleto.getId();
        final var expectedStatus = BoletoStatus.REGISTRADO;

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        when(cobrancaGateway.findByConvenioAndNumeroTituloCliente(any(), any()))
                .thenReturn(Optional.of(cobranca));

        // when
        useCase.execute(expectedId.getValue());

        // then
        verify(boletoGateway).findById(eq(expectedId));
        verify(cobrancaGateway)
                .findByConvenioAndNumeroTituloCliente(
                        eq(expectedConvenio), eq(expectedNumeroTituloCliente)
                );
        final var captor = ArgumentCaptor.forClass(Boleto.class);
        verify(boletoGateway).update(captor.capture());
        final var boletoUpdated = captor.getValue();
        Assertions.assertNotNull(boletoUpdated);
        Assertions.assertEquals(expectedId, boletoUpdated.getId());
        Assertions.assertEquals(expectedConvenio, boletoUpdated.getConvenio());
        Assertions.assertEquals(expectedNumeroTituloCliente, boletoUpdated.getNumeroTituloCliente());
        Assertions.assertEquals(expectedStatus, boletoUpdated.getStatus());
        Assertions.assertEquals(boleto.getCriadoEm(), boletoUpdated.getCriadoEm());
//        Assertions.assertTrue(boleto.getAtualizadoEm().isBefore(boletoUpdated.getAtualizadoEm()));
    }

    @Test
    void givenAnInvalidBoletoId_whenCallsConfirmaRegistro_shouldThrowsDomainException() {
        // given
        final var invalidId = BoletoID.from("invalid-id");
        final var expectedErrorMessage = "'boleto' not found";
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
        final var expectedNumeroTituloCliente = "12345678901234567890";

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNumeroTituloCliente);
        boleto.publishDomainEvent(publisher);

        final var expectedId = boleto.getId();
        final var expectedStatus = BoletoStatus.NAO_REGISTRADO;

        when(boletoGateway.findById(any()))
                .thenReturn(Optional.of(boleto));

        when(cobrancaGateway.findByConvenioAndNumeroTituloCliente(any(), any()))
                .thenReturn(Optional.empty());

        // when
        useCase.execute(expectedId.getValue());

        // then
        verify(boletoGateway).findById(eq(expectedId));
        verify(cobrancaGateway)
                .findByConvenioAndNumeroTituloCliente(
                        eq(expectedConvenio), eq(expectedNumeroTituloCliente)
                );
        final var captor = ArgumentCaptor.forClass(Boleto.class);
        verify(boletoGateway).update(captor.capture());
        final var boletoUpdated = captor.getValue();
        Assertions.assertNotNull(boletoUpdated);
        Assertions.assertEquals(expectedId, boletoUpdated.getId());
        Assertions.assertEquals(expectedConvenio, boletoUpdated.getConvenio());
        Assertions.assertEquals(expectedNumeroTituloCliente, boletoUpdated.getNumeroTituloCliente());
        Assertions.assertEquals(expectedStatus, boletoUpdated.getStatus());
        Assertions.assertEquals(boleto.getCriadoEm(), boletoUpdated.getCriadoEm());
    }
}