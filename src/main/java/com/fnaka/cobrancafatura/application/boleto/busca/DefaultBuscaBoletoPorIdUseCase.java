package com.fnaka.cobrancafatura.application.boleto.busca;

import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;

public class DefaultBuscaBoletoPorIdUseCase extends BuscaBoletoPorIdUseCase {

    private final BoletoGateway boletoGateway;

    public DefaultBuscaBoletoPorIdUseCase(BoletoGateway boletoGateway) {
        this.boletoGateway = boletoGateway;
    }

    @Override
    public BoletoOutput execute(String anId) {
        final var boletoId = BoletoID.from(anId);
        return this.boletoGateway.findById(boletoId)
                .map(BoletoOutput::from)
                .orElseThrow(() -> DomainException.with(Error.with(ErrorCode.CFA_006)));
    }
}
