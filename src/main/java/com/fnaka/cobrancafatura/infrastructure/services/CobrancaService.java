package com.fnaka.cobrancafatura.infrastructure.services;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;

public interface CobrancaService {

    Cobranca detalhaCobrancaBoleto(Integer convenio, String numeroTituloCliente);
}
