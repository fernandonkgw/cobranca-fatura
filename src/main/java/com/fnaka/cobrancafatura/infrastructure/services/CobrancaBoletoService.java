package com.fnaka.cobrancafatura.infrastructure.services;

import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoleto;
import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;
import com.fnaka.cobrancafatura.domain.dtos.CobrancaBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.dtos.PixBoletoRequisicao;

public interface CobrancaBoletoService {

    CobrancaBoletoRequisicao findByNossoNumeroAndConvenio(String nossoNumero, Integer convenio);

    PixBoletoRequisicao createPix(String nossoNumero, Integer convenio);
}
