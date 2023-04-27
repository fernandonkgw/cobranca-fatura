package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;

import java.time.Instant;

public record PixCriadoEvent(String id, BoletoStatus status, Instant occurredOn) implements DomainEvent {

    public PixCriadoEvent(String id) {
        this(id, BoletoStatus.PIX_CRIADO, InstantUtils.now());
    }
}
