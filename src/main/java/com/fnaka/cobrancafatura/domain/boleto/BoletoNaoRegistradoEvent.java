package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;

import java.time.Instant;

public record BoletoNaoRegistradoEvent(String id, Instant occurredOn) implements DomainEvent {
    public BoletoNaoRegistradoEvent(String id) {
        this(id, InstantUtils.now());
    }
}
