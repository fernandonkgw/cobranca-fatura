package com.fnaka.cobrancafatura.domain.boleto;

import java.util.Optional;

public interface CobrancaGateway {

    Optional<Cobranca> findByConvenioAndNossoNumero(Integer convenio, String nossoNumero);

    PixBoleto createPix(Integer convenio, String nossoNumero);
}
