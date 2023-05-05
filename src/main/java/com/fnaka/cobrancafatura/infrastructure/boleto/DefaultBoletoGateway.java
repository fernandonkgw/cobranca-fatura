package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.infrastructure.boleto.event.BoletoCriadoEvent;
import com.fnaka.cobrancafatura.infrastructure.boleto.event.BoletoRegistradoEvent;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DefaultBoletoGateway implements BoletoGateway {

    private final BoletoRepository boletoRepository;
    private final ApplicationEventMulticaster applicationEventMulticaster;

    public DefaultBoletoGateway(
            BoletoRepository boletoRepository,
            ApplicationEventMulticaster applicationEventMulticaster
    ) {
        this.boletoRepository = boletoRepository;
        this.applicationEventMulticaster = applicationEventMulticaster;
    }

    @Override
    public Boleto create(Boleto boleto) {
        final var result = this.boletoRepository.save(BoletoJpaEntity.from(boleto))
                .toAggregate();

        applicationEventMulticaster.multicastEvent(new BoletoCriadoEvent(this, boleto));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boleto> findById(BoletoID id) {
        return this.boletoRepository.findById(id.getValue())
                .map(BoletoJpaEntity::toAggregate);
    }

    @Override
    public Boleto update(Boleto boleto) {
        final var result = this.boletoRepository.save(BoletoJpaEntity.from(boleto))
                .toAggregate();

        if (boleto.isRegistrado()) {
            applicationEventMulticaster.multicastEvent(new BoletoRegistradoEvent(this, boleto));
        }

        return result;
    }
}
