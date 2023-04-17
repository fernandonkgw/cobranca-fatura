package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.AggregateRoot;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;
import com.fnaka.cobrancafatura.domain.validation.handler.ThrowsValidationHandler;

import java.time.Instant;

public class Boleto extends AggregateRoot<BoletoID> {

    private Integer convenio;
    private String numeroTituloCliente;

    private BoletoStatus status;
    private Instant criadoEm;
    private Instant atualizadoEm;

    protected Boleto(
            final BoletoID anId,
            final Integer convenio,
            final String numeroTituloCliente,
            final BoletoStatus status,
            final Instant criadoEm,
            final Instant atualizadoEm
    ) {
        super(anId);
        this.convenio = convenio;
        this.numeroTituloCliente = numeroTituloCliente;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        selfValidate();
    }

    public static Boleto newBoleto(final Integer convenio, final String numeroTituloCliente) {
        final var anId = BoletoID.unique();
        final var agora = InstantUtils.now();
        final var result = new Boleto(anId, convenio, numeroTituloCliente, BoletoStatus.CRIADO, agora, agora);
        result.registerEvent(new BoletoCriadoEvent(anId.getValue()));
        return result;
    }

    public static Boleto with(
            final BoletoID id,
            final Integer convenio,
            final String numeroTituloCliente,
            final BoletoStatus status,
            final Instant criadoEm,
            final Instant atualizadoEm
    ) {
        return new Boleto(id, convenio, numeroTituloCliente, status, criadoEm, atualizadoEm);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new BoletoValidator(this, handler).validate();
    }

    public Integer getConvenio() {
        return convenio;
    }

    public String getNumeroTituloCliente() {
        return numeroTituloCliente;
    }

    public BoletoStatus getStatus() {
        return status;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    private void selfValidate() {
        validate(new ThrowsValidationHandler());
    }
}
