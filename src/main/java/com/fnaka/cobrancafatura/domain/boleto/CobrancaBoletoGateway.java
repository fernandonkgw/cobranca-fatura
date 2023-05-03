package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.dtos.CobrancaBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.dtos.PixBoletoRequisicao;

import java.util.Optional;

public interface CobrancaBoletoGateway {

    CobrancaBoletoRequisicao findByNossoNumeroAndConvenio(String nossoNumero, Integer convenio);

    PixBoletoRequisicao createPix(String nossoNumero, Integer convenio);
}
