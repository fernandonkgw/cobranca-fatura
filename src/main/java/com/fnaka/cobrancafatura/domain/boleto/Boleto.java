package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.AggregateRoot;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;
import com.fnaka.cobrancafatura.domain.validation.handler.ThrowsValidationHandler;

import java.time.Instant;

public class Boleto extends AggregateRoot<BoletoID> {

    private Integer convenio;
    private String nossoNumero;
    private BoletoStatus status;
    private Instant criadoEm;
    private Instant atualizadoEm;
    private PixBoleto pix;

    protected Boleto(
            final BoletoID anId,
            final Integer convenio,
            final String nossoNumero,
            final BoletoStatus status,
            final Instant criadoEm,
            final Instant atualizadoEm,
            final PixBoleto pix
    ) {
        super(anId);
        this.convenio = convenio;
        this.nossoNumero = nossoNumero;
        this.status = status;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.pix = pix;
        selfValidate();
    }

    public static Boleto newBoleto(final Integer convenio, final String nossoNumero) {
        final var anId = BoletoID.unique();
        final var agora = InstantUtils.now();
        final var result = new Boleto(anId, convenio, nossoNumero, BoletoStatus.CRIADO, agora, agora, null);
        return result;
    }

    public static Boleto with(
            final BoletoID id,
            final Integer convenio,
            final String nossoNumero,
            final BoletoStatus status,
            final Instant criadoEm,
            final Instant atualizadoEm
    ) {
        return new Boleto(id, convenio, nossoNumero, status, criadoEm, atualizadoEm, null);
    }

    public static Boleto with(
            final BoletoID id,
            final Integer convenio,
            final String nossoNumero,
            final BoletoStatus status,
            final Instant criadoEm,
            final Instant atualizadoEm,
            final String url,
            final String txId,
            final String emv
    ) {
        if (url != null && txId != null && emv != null) {
            final var pix = new PixBoleto(url, txId, emv);
            return new Boleto(id, convenio, nossoNumero, status, criadoEm, atualizadoEm, pix);
        }
        return with(id, convenio, nossoNumero, status, criadoEm, atualizadoEm);
    }

    public Boleto confirmaRegistro() {
        this.status = BoletoStatus.REGISTRADO;
        this.atualizadoEm = InstantUtils.now();
        return this;
    }

    public boolean isRegistrado() {
        return BoletoStatus.REGISTRADO.equals(this.status);
    }

    public Boleto registroNaoEncontrado() {
        this.status = BoletoStatus.NAO_REGISTRADO;
        this.atualizadoEm = InstantUtils.now();
        this.registerEvent(new BoletoNaoRegistradoEvent(this.getId().getValue()));
        return this;
    }

    public boolean isNaoRegistrado() {
        return BoletoStatus.NAO_REGISTRADO.equals(this.status);
    }

    public Boleto criaPix(PixBoleto pixBoleto) {
        this.pix = pixBoleto;
        this.status = BoletoStatus.PIX_CRIADO;
        this.atualizadoEm = InstantUtils.now();
        return this;
    }

    public Boleto pixNaoCriado() {
        this.status = BoletoStatus.PIX_NAO_CRIADO;
        this.atualizadoEm = InstantUtils.now();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new BoletoValidator(this, handler).validate();
    }

    public Integer getConvenio() {
        return convenio;
    }

    public String getNossoNumero() {
        return nossoNumero;
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

    public PixBoleto getPix() {
        return pix;
    }

    private void selfValidate() {
        validate(new ThrowsValidationHandler());
    }

    public EventoBoleto newEvento() {
        return EventoBoleto.newEvento(this);
    }

    public boolean isCriado() {
        return BoletoStatus.CRIADO.equals(getStatus());
    }

    public boolean isPixCriado() {
        return BoletoStatus.PIX_CRIADO.equals(getStatus());
    }
}
