package com.fnaka.cobrancafatura.domain.boleto;

import java.util.Optional;

public interface CobrancaGateway {

    Optional<Cobranca> findByConvenioAndNumeroTituloCliente(Integer convenio, String numeroTituloCliente);

    PixBoleto createPix(Integer convenio, String numeroTituloCliente);
}
