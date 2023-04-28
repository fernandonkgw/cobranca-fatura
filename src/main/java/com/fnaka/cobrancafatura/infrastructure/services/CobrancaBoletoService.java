package com.fnaka.cobrancafatura.infrastructure.services;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;
import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;

public interface CobrancaBoletoService {

    Cobranca detalhaCobrancaBoleto(Integer convenio, String nossoNumero);

    PixBoleto createPix(Integer convenio, String nossoNumero);
}
