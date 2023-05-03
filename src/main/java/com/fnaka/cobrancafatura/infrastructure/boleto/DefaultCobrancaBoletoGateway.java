package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoleto;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;
import com.fnaka.cobrancafatura.domain.dtos.CobrancaBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.dtos.PixBoletoRequisicao;
import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultCobrancaBoletoGateway implements CobrancaBoletoGateway {

    private final CobrancaBoletoService cobrancaBoletoService;

    public DefaultCobrancaBoletoGateway(CobrancaBoletoService cobrancaBoletoService) {
        this.cobrancaBoletoService = cobrancaBoletoService;
    }

    @Override
    public CobrancaBoletoRequisicao findByNossoNumeroAndConvenio(String nossoNumero, Integer convenio) {
        return cobrancaBoletoService.findByNossoNumeroAndConvenio(nossoNumero, convenio);
    }

    @Override
    public PixBoletoRequisicao createPix(String nossoNumero, Integer convenio) {
        return cobrancaBoletoService.createPix(nossoNumero, convenio);
    }
}
