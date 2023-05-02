package com.fnaka.cobrancafatura.application.boleto.confirmaregistro;

import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;

public class DefaultConfirmaRegistroPorIdUseCase extends ConfirmaRegistroPorIdUseCase {

    private final BoletoGateway boletoGateway;
    private final CobrancaGateway cobrancaGateway;
    private final EventoBoletoGateway eventoBoletoGateway;

    public DefaultConfirmaRegistroPorIdUseCase(
            BoletoGateway boletoGateway,
            CobrancaGateway cobrancaGateway,
            EventoBoletoGateway eventoBoletoGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaGateway = cobrancaGateway;
        this.eventoBoletoGateway = eventoBoletoGateway;
    }

    @Override
    public void execute(String anId) {
        final var boletoId = BoletoID.from(anId);
        final var boleto = this.boletoGateway.findById(boletoId)
                .orElseThrow(() -> DomainException.with(Error.with(ErrorCode.CFA_006)));

        final var convenio = boleto.getConvenio();
        final var nossoNumero = boleto.getNossoNumero();

        final var evento = boleto.newEvento();

        if (cobrancaGateway.findByConvenioAndNossoNumero(convenio, nossoNumero).isPresent()) {
            boleto.confirmaRegistro();
        } else {
            boleto.registroNaoEncontrado();
        }

        evento.concluido(boleto);

        this.boletoGateway.update(boleto);
        this.eventoBoletoGateway.create(evento);
    }
}
