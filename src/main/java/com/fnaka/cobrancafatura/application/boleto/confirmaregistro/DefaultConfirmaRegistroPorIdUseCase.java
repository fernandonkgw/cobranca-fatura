package com.fnaka.cobrancafatura.application.boleto.confirmaregistro;

import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoletoGateway;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;

public class DefaultConfirmaRegistroPorIdUseCase extends ConfirmaRegistroPorIdUseCase {

    private final BoletoGateway boletoGateway;
    private final CobrancaBoletoGateway cobrancaBoletoGateway;
    private final EventoBoletoGateway eventoBoletoGateway;

    public DefaultConfirmaRegistroPorIdUseCase(
            BoletoGateway boletoGateway,
            CobrancaBoletoGateway cobrancaBoletoGateway,
            EventoBoletoGateway eventoBoletoGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaBoletoGateway = cobrancaBoletoGateway;
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

        final var cobrancaBoletoRequisicao = cobrancaBoletoGateway.findByNossoNumeroAndConvenio(nossoNumero, convenio);
        final var cobrancaBoleto = cobrancaBoletoRequisicao.cobrancaBoleto();
        final var requisicao = cobrancaBoletoRequisicao.requisicao();

        if (cobrancaBoleto != null) {
            boleto.confirmaRegistro();
        } else {
            boleto.registroNaoEncontrado();
        }

        evento.concluido(boleto, requisicao);

        this.boletoGateway.update(boleto);
        this.eventoBoletoGateway.create(evento);
    }
}
