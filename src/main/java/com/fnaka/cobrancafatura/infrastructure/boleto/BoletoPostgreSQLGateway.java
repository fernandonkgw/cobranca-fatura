package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import org.springframework.stereotype.Component;

@Component
public class BoletoPostgreSQLGateway implements BoletoGateway {

    private final BoletoRepository boletoRepository;

    public BoletoPostgreSQLGateway(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    @Override
    public Boleto create(Boleto boleto) {
        return this.boletoRepository.save(BoletoJpaEntity.from(boleto))
                .toAggregate();
    }
}
