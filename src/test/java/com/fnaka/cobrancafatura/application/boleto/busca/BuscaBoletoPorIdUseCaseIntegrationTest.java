package com.fnaka.cobrancafatura.application.boleto.busca;

import com.fnaka.cobrancafatura.IntegrationTest;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;

@IntegrationTest
class BuscaBoletoPorIdUseCaseIntegrationTest {

    @Autowired
    private BuscaBoletoPorIdUseCase useCase;

    @Autowired
    private BoletoRepository boletoRepository;

    @SpyBean
    private BoletoGateway boletoGateway;

    @Test
    void givenAValidId_whenCallsBuscaBoletoPorId_shouldReturnOutput() {
        // given
        final var expectedConvenio = 1234567;
        final var expectedNossoNumero = "12345678901234567890";
        final var expectedStatus = BoletoStatus.CRIADO;

        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedId = boleto.getId();

        boletoRepository.saveAndFlush(BoletoJpaEntity.from(boleto));

        Assertions.assertEquals(1, boletoRepository.count());

        // when
        final var actualOutput = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
        Assertions.assertEquals(expectedConvenio, actualOutput.convenio());
        Assertions.assertEquals(expectedNossoNumero, actualOutput.nossoNumero());
        Assertions.assertEquals(expectedStatus, actualOutput.status());
        Assertions.assertEquals(boleto.getCriadoEm(), actualOutput.criadoEm());
        Assertions.assertEquals(boleto.getAtualizadoEm(), actualOutput.atualizadoEm());
    }

    @Test
    void givenAnInvalidId_whenCallsBuscaBoletoPorId_shouldThrowsDomainException() {
        // given
        final var expectedId = BoletoID.from("invalid");

        final var expectedErrorMessage = "'boleto' not found";

        // when
        final var actualException = Assertions.assertThrows(
                DomainException.class, () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getFirstError().message());
    }
}
