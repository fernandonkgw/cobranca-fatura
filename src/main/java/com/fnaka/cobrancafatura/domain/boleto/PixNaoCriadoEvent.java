package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;

import java.time.Instant;

public record PixNaoCriadoEvent(String id, BoletoStatus status, Instant occurredOn) implements DomainEvent {

    public PixNaoCriadoEvent(String id) {
        this(id, BoletoStatus.PIX_NAO_CRIADO, InstantUtils.now());
    }
}
