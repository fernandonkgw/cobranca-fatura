package com.fnaka.cobrancafatura.infrastructure.boleto.event;

import com.fnaka.cobrancafatura.application.boleto.confirmaregistro.ConfirmaRegistroPorIdUseCase;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BoletoCriadoListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoletoCriadoListener.class);

    private final ConfirmaRegistroPorIdUseCase confirmaRegistroPorIdUseCase;

    public BoletoCriadoListener(ConfirmaRegistroPorIdUseCase confirmaRegistroPorIdUseCase) {
        this.confirmaRegistroPorIdUseCase = confirmaRegistroPorIdUseCase;
    }

    @EventListener(BoletoCriadoEvent.class)
    public void onBoletoCriado(BoletoCriadoEvent event) {
        final var boletoId = event.getBoleto().getId().getValue();
        LOGGER.info(Json.writeValueAsString(event.getBoleto()));
        try {
            this.confirmaRegistroPorIdUseCase.execute(boletoId);
        } catch (DomainException e) {
            LOGGER.error("Message {} param {}", e.getFirstError().message(), e.getFirstError().getFirstParam());
        }
    }
}
