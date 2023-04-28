package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;
import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultCobrancaGateway implements CobrancaGateway {

    private final CobrancaBoletoService cobrancaBoletoService;

    public DefaultCobrancaGateway(CobrancaBoletoService cobrancaBoletoService) {
        this.cobrancaBoletoService = cobrancaBoletoService;
    }

    @Override
    public Optional<Cobranca> findByConvenioAndNossoNumero(Integer convenio, String nossoNumero) {

        try {
            return Optional.of(cobrancaBoletoService.detalhaCobrancaBoleto(convenio, nossoNumero));
        } catch (BadRequestException e) {
            return Optional.empty();
        }
    }

    @Override
    public PixBoleto createPix(Integer convenio, String nossoNumero) {
        return cobrancaBoletoService.createPix(convenio, nossoNumero);
    }
}
