package com.fnaka.cobrancafatura.infrastructure.eventoboleto.event;

import com.fnaka.cobrancafatura.application.boleto.criapix.CriaPixBoletoUseCase;
import com.fnaka.cobrancafatura.domain.exceptions.DomainException;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BoletoRegistradoListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoletoRegistradoListener.class);

    private final CriaPixBoletoUseCase criaPixBoletoUseCase;

    public BoletoRegistradoListener(CriaPixBoletoUseCase criaPixBoletoUseCase) {
        this.criaPixBoletoUseCase = criaPixBoletoUseCase;
    }

    @EventListener(BoletoRegistradoEvent.class)
    public void onBoletoRegistrado(BoletoRegistradoEvent event) {
        final var boletoId = event.getEventoBoleto().getBoletoId().getValue();
        LOGGER.info(Json.writeValueAsString(event.getEventoBoleto()));
        try {
            this.criaPixBoletoUseCase.execute(boletoId);
        } catch (DomainException e) {
            LOGGER.error("Message {} param {}", e.getFirstError().message(), e.getFirstError().getFirstParam());
        }
    }
}
