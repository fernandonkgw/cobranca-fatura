package com.fnaka.cobrancafatura.infrastructure.boleto.persistence;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "Boleto")
@Table(name = "boletos")
public class BoletoJpaEntity {

    @Id
    private String id;

    @Column(name = "convenio", nullable = false)
    private Integer convenio;

    @Column(name = "nosso_numero", nullable = false)
    private String nossoNumero;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BoletoStatus status;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    @Column(name = "url")
    private String url;

    @Column(name = "txid")
    private String txId;

    @Column(name = "emv")
    private String emv;

    public BoletoJpaEntity() {
    }

    private BoletoJpaEntity(
            final String id,
            final Integer convenio,
            final String nossoNumero,
            final BoletoStatus status,
            final Instant criadoEm,
            final Instant atualizadoEm,
            final String url,
            final String txId,
            final String emv
    ) {
        this.id = id;
        this.convenio = convenio;
        this.nossoNumero = nossoNumero;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.url = url;
        this.txId = txId;
        this.emv = emv;
    }

    public static BoletoJpaEntity from(final Boleto boleto) {
        final var pix = boleto.getPix();
        final var url = pix != null ? pix.url() : null;
        final var txId = pix != null ? pix.txId() : null;
        final var emv = pix != null ? pix.emv() : null;
        return new BoletoJpaEntity(
                boleto.getId().getValue(),
                boleto.getConvenio(),
                boleto.getNossoNumero(),
                boleto.getStatus(),
                boleto.getCriadoEm(),
                boleto.getAtualizadoEm(),
                url,
                txId,
                emv
        );
    }

    public Boleto toAggregate() {
        return Boleto.with(
                BoletoID.from(getId()),
                getConvenio(),
                getNossoNumero(),
                getStatus(),
                getCriadoEm(),
                getAtualizadoEm(),
                getUrl(),
                getTxId(),
                getEmv()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getConvenio() {
        return convenio;
    }

    public void setConvenio(Integer convenio) {
        this.convenio = convenio;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String numeroTituloCliente) {
        this.nossoNumero = numeroTituloCliente;
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

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getEmv() {
        return emv;
    }

    public void setEmv(String emv) {
        this.emv = emv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BoletoJpaEntity that = (BoletoJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
