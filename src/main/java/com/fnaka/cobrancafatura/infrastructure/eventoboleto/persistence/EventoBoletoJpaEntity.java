package com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence;

import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoID;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "EventoBoleto")
@Table(name = "eventos_boleto")
public class EventoBoletoJpaEntity {

    @Id
    private String id;

    @Column(name = "boleto_id", nullable = false)
    private String boletoId;

    @Column(name = "status", nullable = false)
    private BoletoStatus status;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    @Column(name = "tempo_execucao")
    private Long executadoEm;

    @Column(name = "url")
    private String urlRequisicao;

    @Column(name = "payload_request")
    private String payloadRequest;

    @Column(name = "payload_response")
    private String payloadResponse;

    public EventoBoletoJpaEntity() {
    }

    private EventoBoletoJpaEntity(
            String id,
            String boletoId,
            BoletoStatus status,
            Instant criadoEm,
            Long executadoEm,
            String urlRequisicao,
            String payloadRequest,
            String payloadResponse
    ) {
        this.id = id;
        this.boletoId = boletoId;
        this.status = status;
        this.criadoEm = criadoEm;
        this.executadoEm = executadoEm;
        this.urlRequisicao = urlRequisicao;
        this.payloadRequest = payloadRequest;
        this.payloadResponse = payloadResponse;
    }

    public static EventoBoletoJpaEntity from(final EventoBoleto eventoBoleto) {
        final var requisicao = eventoBoleto.getRequisicao();
        final var url = requisicao != null ? requisicao.url() : null;
        final var request = requisicao != null ? requisicao.payloadRequest() : null;
        final var response = requisicao != null ? requisicao.payloadResponse() : null;
        final var tempoResponse = requisicao != null ? requisicao.responseTime() : null;
        return new EventoBoletoJpaEntity(
                eventoBoleto.getId().getValue(),
                eventoBoleto.getBoletoId().getValue(),
                eventoBoleto.getStatus(),
                eventoBoleto.getCriadoEm(),
                tempoResponse,
                url,
                request,
                response
        );
    }

    public EventoBoleto toAggregate() {
        return EventoBoleto.with(
                EventoBoletoID.from(this.id),
                BoletoID.from(this.boletoId),
                this.status,
                this.criadoEm,
                this.executadoEm,
                this.urlRequisicao,
                this.payloadRequest,
                this.payloadResponse
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(String boletoId) {
        this.boletoId = boletoId;
    }

    public BoletoStatus getStatus() {
        return status;
    }

    public void setStatus(BoletoStatus status) {
        this.status = status;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getExecutadoEm() {
        return executadoEm;
    }

    public void setExecutadoEm(Long tempoExecucao) {
        this.executadoEm = tempoExecucao;
    }

    public String getUrlRequisicao() {
        return urlRequisicao;
    }

    public void setUrlRequisicao(String urlRequisicao) {
        this.urlRequisicao = urlRequisicao;
    }

    public String getPayloadRequest() {
        return payloadRequest;
    }

    public void setPayloadRequest(String payloadRequest) {
        this.payloadRequest = payloadRequest;
    }

    public String getPayloadResponse() {
        return payloadResponse;
    }

    public void setPayloadResponse(String payloadResponse) {
        this.payloadResponse = payloadResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoBoletoJpaEntity that = (EventoBoletoJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
