package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;

import java.time.Instant;

public record BoletoCriadoEvent(String id, BoletoStatus status, Instant occurredOn) implements DomainEvent {

    public BoletoCriadoEvent(String id) {
        this(id, BoletoStatus.CRIADO, InstantUtils.now());
    }
}
