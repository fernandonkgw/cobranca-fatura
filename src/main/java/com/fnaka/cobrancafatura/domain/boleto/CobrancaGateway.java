package com.fnaka.cobrancafatura.domain.boleto;

import java.util.Optional;

public interface CobrancaGateway {

    Optional<Cobranca> buscaPorConvenioAndNumeroTituloCliente(Integer convenio, String numeroTituloCliente);
}
