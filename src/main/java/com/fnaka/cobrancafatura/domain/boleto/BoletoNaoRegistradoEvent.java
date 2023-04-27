package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;

import java.time.Instant;

public record BoletoNaoRegistradoEvent(String id, BoletoStatus status, Instant occurredOn) implements DomainEvent {
    public BoletoNaoRegistradoEvent(String id) {
        this(id, BoletoStatus.NAO_REGISTRADO, InstantUtils.now());
    }
}
