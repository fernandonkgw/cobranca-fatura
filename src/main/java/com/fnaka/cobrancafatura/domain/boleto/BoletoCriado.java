package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;

import java.time.Instant;

public record BoletoCriado(String id, Instant occurredOn) implements DomainEvent {

    public BoletoCriado(String id) {
        this(id, InstantUtils.now());
    }
}
