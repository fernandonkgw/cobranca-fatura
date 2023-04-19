package com.fnaka.cobrancafatura.application.boleto.confirmaregistro;

import com.fnaka.cobrancafatura.application.boleto.busca.BoletoOutput;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

public class DefaultConfirmaRegistroPorIdUseCase extends ConfirmaRegistroPorIdUseCase {

    private final BoletoGateway boletoGateway;
    private final CobrancaGateway cobrancaGateway;

    public DefaultConfirmaRegistroPorIdUseCase(
            BoletoGateway boletoGateway,
            CobrancaGateway cobrancaGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaGateway = cobrancaGateway;
    }

    @Override
    public void execute(String anId) {
        final var boletoId = BoletoID.from(anId);
        final var boleto = this.boletoGateway.findById(boletoId)
                .orElseThrow(() -> DomainException.with(Error.with(ErrorCode.CFA_006)));

        final var convenio = boleto.getConvenio();
        final var numeroTituloCliente = boleto.getNumeroTituloCliente();

        cobrancaGateway.confirmaRegistro(convenio, numeroTituloCliente);
        boleto.registroConfirmado();

        this.boletoGateway.update(boleto);
    }
}
