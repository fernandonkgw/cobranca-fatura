package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;

import java.time.Instant;

public record BoletoRegistradoEvent(String id, BoletoStatus status, Instant occurredOn) implements DomainEvent {

    public BoletoRegistradoEvent(String id) {
        this(id, BoletoStatus.REGISTRADO, InstantUtils.now());
    }
}
