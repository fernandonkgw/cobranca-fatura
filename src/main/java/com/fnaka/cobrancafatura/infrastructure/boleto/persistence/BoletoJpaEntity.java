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

    @Column(name = "numero_titulo_cliente", nullable = false)
    private String numeroTituloCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BoletoStatus status;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    public BoletoJpaEntity() {
    }

    private BoletoJpaEntity(
            final String id,
            final Integer convenio,
            final String numeroTituloCliente,
            final BoletoStatus status,
            final Instant criadoEm,
            final Instant atualizadoEm
    ) {
        this.id = id;
        this.convenio = convenio;
        this.numeroTituloCliente = numeroTituloCliente;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public static BoletoJpaEntity from(final Boleto boleto) {
        return new BoletoJpaEntity(
                boleto.getId().getValue(),
                boleto.getConvenio(),
                boleto.getNossoNumero(),
                boleto.getStatus(),
                boleto.getCriadoEm(),
                boleto.getAtualizadoEm()
        );
    }

    public Boleto toAggregate() {
        return Boleto.with(
                BoletoID.from(getId()),
                getConvenio(),
                getNumeroTituloCliente(),
                getStatus(),
                getCriadoEm(),
                getAtualizadoEm()
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

    public String getNumeroTituloCliente() {
        return numeroTituloCliente;
    }

    public void setNumeroTituloCliente(String numeroTituloCliente) {
        this.numeroTituloCliente = numeroTituloCliente;
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
