package com.fnaka.cobrancafatura.application.eventoboleto.lista;

import com.fnaka.cobrancafatura.UseCaseTest;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ListaEventosBoletoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListaEventosBoletoUseCase useCase;

    @Mock
    private EventoBoletoGateway eventoBoletoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(eventoBoletoGateway);
    }

    @Test
    void givenPersistedEventoBoleto_whenCallsListaEventosBoleto_shoulReturnOutput() {
        // given
        final var expectedNossoNumero = "00031285571000000033";
        final var expectedConvenio = 3128557;
        final var boleto = Boleto.newBoleto(expectedConvenio, expectedNossoNumero);
        final var expectedBoletoId = boleto.getId();
        final var evento = boleto.newEvento();
        final var expectedStatus = BoletoStatus.CRIADO;

        when(eventoBoletoGateway.findByBoletoId(any()))
                .thenReturn(List.of(evento));

        // when
        final var actualOutputList = useCase.execute(expectedBoletoId.getValue());

        // then
        Assertions.assertNotNull(actualOutputList);
        Assertions.assertFalse(actualOutputList.isEmpty());
        final var actualEventoBoleto = actualOutputList.get(0);
        Assertions.assertNotNull(actualEventoBoleto);
        Assertions.assertNotNull(actualEventoBoleto.id());
        Assertions.assertEquals(expectedBoletoId.getValue(), actualEventoBoleto.boletoId());
        Assertions.assertEquals(expectedBoletoId.getValue(), actualEventoBoleto.boletoId());
        Assertions.assertEquals(expectedStatus, actualEventoBoleto.status());
        Assertions.assertNull(actualEventoBoleto.requisicao());
    }
}