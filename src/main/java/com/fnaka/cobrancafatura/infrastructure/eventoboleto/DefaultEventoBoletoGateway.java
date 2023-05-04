package com.fnaka.cobrancafatura.infrastructure.eventoboleto;

import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.event.BoletoCriadoEvent;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.event.BoletoRegistradoEvent;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence.EventoBoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence.EventoBoletoRepository;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultEventoBoletoGateway implements EventoBoletoGateway {

    private final EventoBoletoRepository eventoBoletoRepository;
    private final ApplicationEventMulticaster applicationEventMulticaster;

    public DefaultEventoBoletoGateway(
            EventoBoletoRepository eventoBoletoRepository,
            ApplicationEventMulticaster applicationEventMulticaster
    ) {
        this.eventoBoletoRepository = eventoBoletoRepository;
        this.applicationEventMulticaster = applicationEventMulticaster;
    }

    @Override
    public EventoBoleto create(EventoBoleto eventoBoleto) {

        final var result = this.eventoBoletoRepository.save(EventoBoletoJpaEntity.from(eventoBoleto))
                .toAggregate();

        switch (eventoBoleto.getStatus()) {
            case CRIADO -> applicationEventMulticaster.multicastEvent(
                    new BoletoCriadoEvent(this, eventoBoleto)
            );
            case REGISTRADO -> applicationEventMulticaster.multicastEvent(
                    new BoletoRegistradoEvent(this, eventoBoleto)
            );
        }

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
