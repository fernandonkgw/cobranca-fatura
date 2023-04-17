package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoCriadoQueue;
import com.fnaka.cobrancafatura.infrastructure.services.EventService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultBoletoGateway implements BoletoGateway {

    private final BoletoRepository boletoRepository;
    private final EventService eventService;

    public DefaultBoletoGateway(
            BoletoRepository boletoRepository,
            @BoletoCriadoQueue EventService eventService
    ) {
        this.boletoRepository = boletoRepository;
        this.eventService = eventService;
    }

    @Override
    public Boleto create(Boleto boleto) {
        final var result = this.boletoRepository.save(BoletoJpaEntity.from(boleto))
                .toAggregate();

        boleto.publishDomainEvents(this.eventService::send);

        return result;
    }

    @Override
    public Optional<Boleto> findById(BoletoID id) {
        return Optional.empty();
    }
}
