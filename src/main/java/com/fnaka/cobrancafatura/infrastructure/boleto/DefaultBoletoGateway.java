package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.BoletoWebhookEvent;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoJpaEntity;
import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoCriadoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoRegistradoQueue;
import com.fnaka.cobrancafatura.infrastructure.services.EventService;
import com.fnaka.cobrancafatura.infrastructure.services.WebhookPubService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DefaultBoletoGateway implements BoletoGateway {

    private final BoletoRepository boletoRepository;
    private final EventService eventServiceBoletoCriado;
    private final EventService eventServiceBoletoRegistrado;
    private final WebhookPubService webhookPubService;

    public DefaultBoletoGateway(
            BoletoRepository boletoRepository,
            @BoletoCriadoQueue EventService eventServiceBoletoCriado,
            @BoletoRegistradoQueue EventService eventServiceBoletoRegistrado, WebhookPubService webhookPubService) {
        this.boletoRepository = boletoRepository;
        this.eventServiceBoletoCriado = eventServiceBoletoCriado;
        this.eventServiceBoletoRegistrado = eventServiceBoletoRegistrado;
        this.webhookPubService = webhookPubService;
    }

    @Override
    @Transactional
    public Boleto create(Boleto boleto) {
        final var result = this.boletoRepository.save(BoletoJpaEntity.from(boleto))
                .toAggregate();

        boleto.publishDomainEvent(this.eventServiceBoletoCriado::send);

        this.webhookPubService.send(BoletoWebhookEvent.from(boleto));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boleto> findById(BoletoID id) {
        return this.boletoRepository.findById(id.getValue())
                .map(BoletoJpaEntity::toAggregate);
    }

    @Override
    @Transactional
    public Boleto update(Boleto boleto) {
        final var result = this.boletoRepository.save(BoletoJpaEntity.from(boleto))
                .toAggregate();

        if (boleto.isRegistrado()) {
            boleto.publishDomainEvent(this.eventServiceBoletoRegistrado::send);
        }
        if (boleto.isNaoRegistrado()) {

        }

        this.webhookPubService.send(BoletoWebhookEvent.from(boleto));

        return result;
    }
}
