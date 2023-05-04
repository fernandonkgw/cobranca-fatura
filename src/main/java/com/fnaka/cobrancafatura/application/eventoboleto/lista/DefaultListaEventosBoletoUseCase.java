package com.fnaka.cobrancafatura.application.eventoboleto.lista;

import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.domain.pagination.Pagination;

import java.util.List;

public class DefaultListaEventosBoletoUseCase extends ListaEventosBoletoUseCase {

    private final EventoBoletoGateway eventoBoletoGateway;

    public DefaultListaEventosBoletoUseCase(EventoBoletoGateway eventoBoletoGateway) {
        this.eventoBoletoGateway = eventoBoletoGateway;
    }

    @Override
    public List<ListaEventosBoletoOutput> execute(String anId) {
        final var boletoId = BoletoID.from(anId);
        return eventoBoletoGateway.findByBoletoId(boletoId)
                .stream()
                .map(ListaEventosBoletoOutput::from)
                .toList();
    }
}
