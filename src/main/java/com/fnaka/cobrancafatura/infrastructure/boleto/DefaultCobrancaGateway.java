package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultCobrancaGateway implements CobrancaGateway {

    private final CobrancaService cobrancaService;

    public DefaultCobrancaGateway(CobrancaService cobrancaService) {
        this.cobrancaService = cobrancaService;
    }

    @Override
    public Optional<Cobranca> buscaPorConvenioAndNumeroTituloCliente(Integer convenio, String numeroTituloCliente) {

        try {
            return Optional.of(cobrancaService.detalhaCobrancaBoleto(convenio, numeroTituloCliente));
        } catch (BadRequestException e) {
            return Optional.empty();
        }

    }
}
