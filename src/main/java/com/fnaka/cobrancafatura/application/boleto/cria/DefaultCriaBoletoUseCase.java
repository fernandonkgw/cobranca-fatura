package com.fnaka.cobrancafatura.application.boleto.cria;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;

public class DefaultCriaBoletoUseCase extends CriaBoletoUseCase{

    private final BoletoGateway boletoGateway;
//    private final EventoBoletoGateway eventoBoletoGateway;

    public DefaultCriaBoletoUseCase(
            BoletoGateway boletoGateway
//            ,
//            EventoBoletoGateway eventoBoletoGateway
    ) {
        this.boletoGateway = boletoGateway;
//        this.eventoBoletoGateway = eventoBoletoGateway;
    }

    @Override
    public CriaBoletoOutput execute(final CriaBoletoCommand aCommand) {
        final var convenio = aCommand.convenio();
        final var nossoNumero = aCommand.nossoNumero();

        final var boleto = Boleto.newBoleto(convenio, nossoNumero);
        final var evento = boleto.newEvento();

        final var boletoCriado = this.boletoGateway.create(boleto);
        evento.concluido(boletoCriado);
        return CriaBoletoOutput.from(boletoCriado);
    }
}
