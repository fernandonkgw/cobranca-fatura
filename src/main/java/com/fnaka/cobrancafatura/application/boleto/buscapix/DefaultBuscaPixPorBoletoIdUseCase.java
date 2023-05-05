package com.fnaka.cobrancafatura.application.boleto.buscapix;

import com.fnaka.cobrancafatura.application.boleto.busca.PixOutput;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;

public class DefaultBuscaPixPorBoletoIdUseCase extends BuscaPixPorBoletoIdUseCase {

    private final BoletoGateway boletoGateway;

    public DefaultBuscaPixPorBoletoIdUseCase(BoletoGateway boletoGateway) {
        this.boletoGateway = boletoGateway;
    }

    @Override
    public PixOutput execute(String anId) {
        final var boletoId = BoletoID.from(anId);

        final var boleto =this.boletoGateway.findById(boletoId)
                .orElseThrow(() -> DomainException.with(Error.with(ErrorCode.CFA_006)));

        final var status = boleto.getStatus();
        if (!boleto.isPixCriado()) {
            throw DomainException.with(Error.with(ErrorCode.CFA_010, status));
        }

        return PixOutput.from(boleto.getPix());
    }
}
