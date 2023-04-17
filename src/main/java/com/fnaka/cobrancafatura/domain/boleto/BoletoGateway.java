package com.fnaka.cobrancafatura.domain.boleto;

import java.util.Optional;

public interface BoletoGateway {

    Boleto create(Boleto boleto);

    Optional<Boleto> findById(BoletoID id);

    Boleto update(Boleto boleto);
}
