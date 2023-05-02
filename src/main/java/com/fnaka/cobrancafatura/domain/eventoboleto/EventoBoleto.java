package com.fnaka.cobrancafatura.domain.eventoboleto;

import com.fnaka.cobrancafatura.domain.Entity;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;

import java.time.Duration;
import java.time.Instant;

public class EventoBoleto extends Entity<EventoBoletoID> {

    private final BoletoID boletoId;
    private BoletoStatus status;
    private final Instant criadoEm;
    private Long executadoEm;

    protected EventoBoleto(
            EventoBoletoID eventoBoletoID,
            BoletoID boletoId,
            BoletoStatus status,
            Instant criadoEm,
            Long executadoEm
    ) {
        super(eventoBoletoID);
        this.boletoId = boletoId;
        this.status = status;
        this.criadoEm = criadoEm;
        this.executadoEm = executadoEm;
    }

    public static EventoBoleto newEvento(final Boleto boleto) {
        final var anId = EventoBoletoID.unique();
        final var boletoId = boleto.getId();
        final var status = boleto.getStatus();
        return new EventoBoleto(anId, boletoId, status, InstantUtils.now(), null);
    }

    public static EventoBoleto with(
            EventoBoletoID id,
            BoletoID boletoID,
            BoletoStatus status,
            Instant criadoEm,
            Long executadoEm
    ) {
        return new EventoBoleto(id, boletoID, status, criadoEm, executadoEm);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public BoletoID getBoletoId() {
        return boletoId;
    }

    public BoletoStatus getStatus() {
        return status;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Long getExecutadoEm() {
        return executadoEm;
    }

    public EventoBoleto concluido(Boleto boleto) {
        this.status = boleto.getStatus();
        final var tempoExecucao = Duration.between(getCriadoEm(), InstantUtils.now()).toMillis();
        this.executadoEm = tempoExecucao;
        return this;
    }
}
