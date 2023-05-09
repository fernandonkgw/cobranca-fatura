package com.fnaka.cobrancafatura.application.boleto.criapix;

import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoletoGateway;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;

public class DefaultCriaPixBoletoUseCase extends CriaPixBoletoUseCase {

    private final BoletoGateway boletoGateway;
    private final CobrancaBoletoGateway cobrancaBoletoGateway;
    private final EventoBoletoGateway eventoBoletoGateway;

    public DefaultCriaPixBoletoUseCase(
            BoletoGateway boletoGateway,
            CobrancaBoletoGateway cobrancaBoletoGateway,
            EventoBoletoGateway eventoBoletoGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaBoletoGateway = cobrancaBoletoGateway;
        this.eventoBoletoGateway = eventoBoletoGateway;
    }

    @Override
    public CriaPixBoletoOutput execute(String anId) {
        final var boletoId = BoletoID.from(anId);

        final var boleto = this.boletoGateway.findById(boletoId)
                .orElseThrow(() -> DomainException.with(Error.with(ErrorCode.CFA_006)));

        if (!boleto.isRegistrado()) {
            throw DomainException.with(Error.with(ErrorCode.CFA_007));
        }

        final var convenio = boleto.getConvenio();
        final var nossoNumero = boleto.getNossoNumero();

        final var pixBoletoRequisicao = cobrancaBoletoGateway.createPix(nossoNumero, convenio);
        final var pixBoleto = pixBoletoRequisicao.pixBoleto();
        final var requisicao = pixBoletoRequisicao.requisicao();
        if (pixBoleto != null) {
            boleto.criaPix(pixBoleto);
        } else {
            boleto.pixNaoCriado();
        }

        final var boletoAtualizado = boletoGateway.update(boleto);
        final var evento = EventoBoleto.newEvento(boletoAtualizado, requisicao);
        this.eventoBoletoGateway.create(evento);

        if (pixBoleto == null) {
            throw DomainException.with(Error.with(ErrorCode.CFA_009, anId));
        }

        return CriaPixBoletoOutput.from(boletoAtualizado.getPix());
    }
}
