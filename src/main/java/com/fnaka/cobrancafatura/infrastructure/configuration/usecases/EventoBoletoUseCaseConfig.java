package com.fnaka.cobrancafatura.infrastructure.configuration.usecases;

import com.fnaka.cobrancafatura.application.eventoboleto.lista.DefaultListaEventosBoletoUseCase;
import com.fnaka.cobrancafatura.application.eventoboleto.lista.ListaEventosBoletoUseCase;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventoBoletoUseCaseConfig {

    private final EventoBoletoGateway eventoBoletoGateway;

    public EventoBoletoUseCaseConfig(EventoBoletoGateway eventoBoletoGateway) {
        this.eventoBoletoGateway = eventoBoletoGateway;
    }

    @Bean
    public ListaEventosBoletoUseCase listaEventosBoletoUseCase() {
        return new DefaultListaEventosBoletoUseCase(eventoBoletoGateway);
    }
}
