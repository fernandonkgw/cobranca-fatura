package com.fnaka.cobrancafatura.infrastructure.eventoboleto.event;

import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;
import org.springframework.context.ApplicationEvent;

public class BoletoRegistradoEvent extends ApplicationEvent {

    private final EventoBoleto eventoBoleto;

    public BoletoRegistradoEvent(Object source, EventoBoleto eventoBoleto) {
        super(source);
        this.eventoBoleto = eventoBoleto;
    }

    public EventoBoleto getEventoBoleto() {
        return eventoBoleto;
    }
}
