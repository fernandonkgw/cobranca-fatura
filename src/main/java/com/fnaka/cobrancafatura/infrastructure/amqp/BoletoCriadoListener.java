package com.fnaka.cobrancafatura.infrastructure.amqp;

import com.fnaka.cobrancafatura.application.boleto.confirmaregistro.ConfirmaRegistroPorIdUseCase;
import com.fnaka.cobrancafatura.domain.boleto.BoletoCriadoEvent;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BoletoCriadoListener {

    private static final Logger LOG = LoggerFactory.getLogger(BoletoCriadoListener.class);

    static final String LISTENER_ID = "boletoCriadoListener";

    private final ConfirmaRegistroPorIdUseCase confirmaRegistroPorIdUseCase;

    public BoletoCriadoListener(ConfirmaRegistroPorIdUseCase confirmaRegistroPorIdUseCase) {
        this.confirmaRegistroPorIdUseCase = confirmaRegistroPorIdUseCase;
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.boleto-criado.queue}")
    public void onBoletoCriado(@Payload final String message) {

        LOG.info("boletoCriado {}", message);
        final var boletoCriado = Json.readValue(message, BoletoCriadoEvent.class);

        try {
            this.confirmaRegistroPorIdUseCase.execute(boletoCriado.id());
        } catch (DomainException e) {
            LOG.error("Message {} param {}", e.getFirstError().message(), e.getFirstError().getFirstParam());
        }
    }
}
