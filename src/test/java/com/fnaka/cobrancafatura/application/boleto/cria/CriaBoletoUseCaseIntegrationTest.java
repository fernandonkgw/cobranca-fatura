package com.fnaka.cobrancafatura.application.boleto.cria;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@IntegrationTest
class CriaBoletoUseCaseIntegrationTest {

    @Autowired
    private CriaBoletoUseCase useCase;

    @Autowired
    private BoletoRepository boletoRepository;

    @SpyBean
    private BoletoGateway boletoGateway;

    @Test
    void givenAValidCommand_whenCallsCriaBoleto_shouldReturnOutput() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";
        final var expectedStatus = BoletoStatus.CRIADO;

        final var command = CriaBoletoCommand.with(expectedConvenio, expectedNossoNumero);

        // when
        final var actualOutput = useCase.execute(command);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        final var actualBoleto = this.boletoRepository.findById(actualOutput.id()).get();


        Assertions.assertEquals(expectedConvenio, actualBoleto.getConvenio());
        Assertions.assertEquals(expectedNossoNumero, actualBoleto.getNossoNumero());
        Assertions.assertEquals(expectedStatus, actualBoleto.getStatus());
        Assertions.assertNotNull(actualBoleto.getCriadoEm());
        Assertions.assertNotNull(actualBoleto.getAtualizadoEm());

        verify(boletoGateway).create(any());
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

