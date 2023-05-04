package com.fnaka.cobrancafatura.infrastructure.eventoboleto;

import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence.EventoBoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence.EventoBoletoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultEventoBoletoGateway implements EventoBoletoGateway {

    private final EventoBoletoRepository eventoBoletoRepository;

    public DefaultEventoBoletoGateway(EventoBoletoRepository eventoBoletoRepository) {
        this.eventoBoletoRepository = eventoBoletoRepository;
    }

    @Override
    public EventoBoleto create(EventoBoleto eventoBoleto) {
        final var result = this.eventoBoletoRepository.save(EventoBoletoJpaEntity.from(eventoBoleto))
                .toAggregate();
        return result;
    }

    @Override
    public List<EventoBoleto> findByBoletoId(BoletoID boletoId) {
        return this.eventoBoletoRepository.findByBoletoId(boletoId.getValue())
                .stream()
                .map(EventoBoletoJpaEntity::toAggregate)
                .toList();
    }
}
