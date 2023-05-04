package com.fnaka.cobrancafatura.infrastructure.eventoboleto;

import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoCriadoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoRegistradoQueue;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence.EventoBoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence.EventoBoletoRepository;
import com.fnaka.cobrancafatura.infrastructure.services.EventService;
import com.fnaka.cobrancafatura.infrastructure.services.WebhookPubService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultEventoBoletoGateway implements EventoBoletoGateway {

    private final EventoBoletoRepository eventoBoletoRepository;
    private final EventService eventServiceBoletoCriado;
    private final EventService eventServiceBoletoRegistrado;

    public DefaultEventoBoletoGateway(
            EventoBoletoRepository eventoBoletoRepository,
            @BoletoCriadoQueue EventService eventServiceBoletoCriado,
            @BoletoRegistradoQueue EventService eventServiceBoletoRegistrado
    ) {
        this.eventoBoletoRepository = eventoBoletoRepository;
        this.eventServiceBoletoCriado = eventServiceBoletoCriado;
        this.eventServiceBoletoRegistrado = eventServiceBoletoRegistrado;
    }

    @Override
    public EventoBoleto create(EventoBoleto eventoBoleto) {
        final var result = this.eventoBoletoRepository.save(EventoBoletoJpaEntity.from(eventoBoleto))
                .toAggregate();

        switch (eventoBoleto.getStatus()) {
            case CRIADO -> eventoBoleto.publishDomainEvent(this.eventServiceBoletoCriado::send);
            case REGISTRADO -> eventoBoleto.publishDomainEvent(this.eventServiceBoletoRegistrado::send);
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
