package com.fnaka.cobrancafatura.domain.eventoboleto;

import com.fnaka.cobrancafatura.domain.AggregateRoot;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;

import java.time.Duration;
import java.time.Instant;

public class EventoBoleto extends AggregateRoot<EventoBoletoID> {

    private final BoletoID boletoId;
    private BoletoStatus status;
    private final Instant criadoEm;
    private Long executadoEm;
    private Requisicao requisicao;

    protected EventoBoleto(
            EventoBoletoID eventoBoletoID,
            BoletoID boletoId,
            BoletoStatus status,
            Instant criadoEm,
            Long executadoEm,
            Requisicao requisicao
    ) {
        super(eventoBoletoID);
        this.boletoId = boletoId;
        this.status = status;
        this.criadoEm = criadoEm;
        this.executadoEm = executadoEm;
        this.requisicao = requisicao;
    }

    public static EventoBoleto newEvento(final Boleto boleto) {
        final var anId = EventoBoletoID.unique();
        final var boletoId = boleto.getId();
        final var status = boleto.getStatus();
        return new EventoBoleto(anId, boletoId, status, InstantUtils.now(), null, null);
    }

    public static EventoBoleto with(
            EventoBoletoID id,
            BoletoID boletoID,
            BoletoStatus status,
            Instant criadoEm,
            Long executadoEm
    ) {
        return new EventoBoleto(id, boletoID, status, criadoEm, executadoEm, null);
    }

    public static EventoBoleto with(
            EventoBoletoID id,
            BoletoID boletoID,
            BoletoStatus status,
            Instant criadoEm,
            Long executadoEm,
            String url,
            String payloadRequest,
            String payloalResponse
    ) {
        if (url == null && payloalResponse == null) {
            return new EventoBoleto(id, boletoID, status, criadoEm, executadoEm, null);
        } else {
            return new EventoBoleto(id, boletoID, status, criadoEm, executadoEm, new Requisicao(url, payloadRequest, payloalResponse));
        }
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

    public Requisicao getRequisicao() {
        return requisicao;
    }

    public EventoBoleto concluido(Boleto boleto) {
        concluido(boleto, null);
        return this;
    }

    public EventoBoleto concluido(Boleto boleto, Requisicao requisicao) {
        this.status = boleto.getStatus();
        this.registerEvent(boleto.getDomainEvent());
        final var tempoExecucao = Duration.between(getCriadoEm(), InstantUtils.now()).toMillis();
        this.executadoEm = tempoExecucao;
        this.requisicao = requisicao;
        return this;
    }
}
