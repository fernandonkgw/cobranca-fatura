package com.fnaka.cobrancafatura.application.boleto.cria;

import com.fnaka.cobrancafatura.UseCaseTest;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CriaBoletoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCriaBoletoUseCase useCase;

    @Mock
    private BoletoGateway boletoGateway;
    @Mock
    private EventoBoletoGateway eventoBoletoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(boletoGateway);
    }

    @Test
    void givenAValidCommand_whenCallsCriaBoleto_shouldReturnOutput() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";
        final var expectedStatus = BoletoStatus.CRIADO;

        final var command = CriaBoletoCommand.with(expectedConvenio, expectedNossoNumero);

        when(boletoGateway.create(ArgumentMatchers.any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(command);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        final var captor = ArgumentCaptor.forClass(Boleto.class);
        verify(boletoGateway).create(captor.capture());
        final var boleto = captor.getValue();
        Assertions.assertNotNull(boleto);
        Assertions.assertNotNull(boleto.getId());
        Assertions.assertEquals(expectedConvenio, boleto.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, boleto.getNossoNumero());
        Assertions.assertEquals(expectedStatus, boleto.getStatus());
        Assertions.assertNotNull(boleto.getCriadoEm());
        Assertions.assertNotNull(boleto.getAtualizadoEm());
    }

    @Test
    void givenAInvalidNullConvenio_whenCallsCriaBoleto_shouldThrowsException() {
        // given
        final var expectedNossoNumero = "12345678901234567890";
        final var expectedErrorMessage = "'convenio' should not be null";
        final var expectedErrorCode = ErrorCode.CFA_001;

        final var command = CriaBoletoCommand.with(null, expectedNossoNumero);

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(command)
        );

        // then
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
        Assertions.assertEquals(expectedErrorCode, actualException.getFirstError().code());
    }
}

