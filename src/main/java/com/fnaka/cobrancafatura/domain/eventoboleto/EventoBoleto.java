package com.fnaka.cobrancafatura.domain.eventoboleto;

import com.fnaka.cobrancafatura.domain.AggregateRoot;
import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;

import java.time.Instant;

public class EventoBoleto extends AggregateRoot<EventoBoletoID> {

    private final Boleto boleto;
    private BoletoStatus status;
    private final Instant criadoEm;
    private Requisicao requisicao;

    protected EventoBoleto(
            EventoBoletoID eventoBoletoID,
            Boleto boleto,
            BoletoStatus status,
            Instant criadoEm,
            Requisicao requisicao
    ) {
        super(eventoBoletoID);
        this.boleto = boleto;
        this.status = status;
        this.criadoEm = criadoEm;
        this.requisicao = requisicao;
    }

    public static EventoBoleto newEvento(final Boleto boleto) {
        return newEvento(boleto, null);
    }

    public static EventoBoleto newEvento(final Boleto boleto, final Requisicao requisicao) {
        final var anId = EventoBoletoID.unique();
        final var status = boleto.getStatus();
        return new EventoBoleto(anId, boleto, status, InstantUtils.now(), requisicao);
    }

    public static EventoBoleto with(
            EventoBoletoID id,
            Boleto boleto,
            BoletoStatus status,
            Instant criadoEm
    ) {
        return new EventoBoleto(id, boleto, status, criadoEm, null);
    }

    public static EventoBoleto with(
            EventoBoletoID id,
            Boleto boleto,
            BoletoStatus status,
            Instant criadoEm,
            Long executadoEm,
            String url,
            String payloadRequest,
            String payloalResponse
    ) {
        if (url == null && payloalResponse == null) {
            return new EventoBoleto(id, boleto, status, criadoEm, null);
        } else {
            final var requisicao = new Requisicao(url, payloadRequest, payloalResponse, executadoEm);
            return new EventoBoleto(id, boleto, status, criadoEm, requisicao);
        }
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public Boleto getBoleto() {
        return boleto;
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
