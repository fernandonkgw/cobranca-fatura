package com.fnaka.cobrancafatura.application.boleto.cria;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;

public class DefaultCriaBoletoUseCase extends CriaBoletoUseCase{

    private final BoletoGateway boletoGateway;

    public DefaultCriaBoletoUseCase(BoletoGateway boletoGateway) {
        this.boletoGateway = boletoGateway;
    }

    @Override
    public CriaBoletoOutput execute(final CriaBoletoCommand aCommand) {
        final var convenio = aCommand.convenio();
        final var numeroTituloCliente = aCommand.numeroTituloCliente();

        final var boleto = Boleto.newBoleto(convenio, numeroTituloCliente);

        return CriaBoletoOutput.from(this.boletoGateway.create(boleto));
    }
}
