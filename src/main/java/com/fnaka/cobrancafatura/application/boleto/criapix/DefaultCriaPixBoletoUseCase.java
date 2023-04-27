package com.fnaka.cobrancafatura.application.boleto.criapix;

import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;

public class DefaultCriaPixBoletoUseCase extends CriaPixBoletoUseCase{

    private final BoletoGateway boletoGateway;
    private final CobrancaGateway cobrancaGateway;

    public DefaultCriaPixBoletoUseCase(
            BoletoGateway boletoGateway, CobrancaGateway cobrancaGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaGateway = cobrancaGateway;
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
        final var numeroTituloCliente = boleto.getNumeroTituloCliente();

        final var pixBoleto = cobrancaGateway.createPix(convenio, numeroTituloCliente);
        boleto.criaPix(pixBoleto);

        return CriaPixBoletoOutput.from(boletoGateway.update(boleto).getPix());
    }
}
