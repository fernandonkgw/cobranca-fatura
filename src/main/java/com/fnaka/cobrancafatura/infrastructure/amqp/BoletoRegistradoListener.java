package com.fnaka.cobrancafatura.infrastructure.amqp;

import com.fnaka.cobrancafatura.application.boleto.criapix.CriaPixBoletoUseCase;
import com.fnaka.cobrancafatura.domain.boleto.BoletoRegistradoEvent;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BoletoRegistradoListener {

    private static final Logger LOG = LoggerFactory.getLogger(BoletoRegistradoListener.class);
    static final String LISTENER_ID = "boletoRegistradoListener";
    private final CriaPixBoletoUseCase criaPixBoletoUseCase;

    public BoletoRegistradoListener(CriaPixBoletoUseCase criaPixBoletoUseCase) {
        this.criaPixBoletoUseCase = criaPixBoletoUseCase;
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.boleto-registrado.queue}")
    public void onBoletoRegistrado(@Payload final String message) {

        LOG.info("boletoRegistrado: {}", message);
        final var boletoRegistrado = Json.readValue(message, BoletoRegistradoEvent.class);

        try {
            this.criaPixBoletoUseCase.execute(boletoRegistrado.id());
        } catch (DomainException e) {
            LOG.error("Message {} param {}", e.getFirstError().message(), e.getFirstError().getFirstParam());
        }
    }
}
