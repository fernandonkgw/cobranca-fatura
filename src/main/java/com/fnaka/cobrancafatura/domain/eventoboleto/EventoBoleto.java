package com.fnaka.cobrancafatura.domain.eventoboleto;

import com.fnaka.cobrancafatura.domain.AggregateRoot;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;

import java.time.Instant;

public class EventoBoleto extends AggregateRoot<EventoBoletoID> {

    private final BoletoID boletoId;
    private BoletoStatus status;
    private final Instant criadoEm;
    private Requisicao requisicao;

    protected EventoBoleto(
            EventoBoletoID eventoBoletoID,
            BoletoID boletoId,
            BoletoStatus status,
            Instant criadoEm,
            Requisicao requisicao
    ) {
        super(eventoBoletoID);
        this.boletoId = boletoId;
        this.status = status;
        this.criadoEm = criadoEm;
        this.requisicao = requisicao;
    }

    public static EventoBoleto newEvento(final Boleto boleto) {
        return newEvento(boleto, null);
    }

    public static EventoBoleto newEvento(final Boleto boleto, final Requisicao requisicao) {
        final var anId = EventoBoletoID.unique();
        final var boletoId = boleto.getId();
        final var status = boleto.getStatus();
        return new EventoBoleto(anId, boletoId, status, InstantUtils.now(), requisicao);
    }

    public static EventoBoleto with(
            EventoBoletoID id,
            BoletoID boletoID,
            BoletoStatus status,
            Instant criadoEm
    ) {
        return new EventoBoleto(id, boletoID, status, criadoEm, null);
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
            return new EventoBoleto(id, boletoID, status, criadoEm, null);
        } else {
            final var requisicao = new Requisicao(url, payloadRequest, payloalResponse, executadoEm);
            return new EventoBoleto(id, boletoID, status, criadoEm, requisicao);
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

    public Requisicao getRequisicao() {
        return requisicao;
    }

}
