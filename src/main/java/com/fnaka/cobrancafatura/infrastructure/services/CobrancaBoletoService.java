package com.fnaka.cobrancafatura.infrastructure.services;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;

public interface CobrancaBoletoService {

    Cobranca detalhaCobrancaBoleto(Integer convenio, String nossoNumero);
}
