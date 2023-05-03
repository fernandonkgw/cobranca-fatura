package com.fnaka.cobrancafatura.infrastructure.configuration.usecases;

import com.fnaka.cobrancafatura.application.boleto.busca.BuscaBoletoPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.busca.DefaultBuscaBoletoPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.confirmaregistro.ConfirmaRegistroPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.confirmaregistro.DefaultConfirmaRegistroPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.DefaultCriaBoletoUseCase;
import com.fnaka.cobrancafatura.application.boleto.criapix.CriaPixBoletoUseCase;
import com.fnaka.cobrancafatura.application.boleto.criapix.DefaultCriaPixBoletoUseCase;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoletoGateway;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoletoUseCaseConfig {

    private final BoletoGateway boletoGateway;
    private final CobrancaBoletoGateway cobrancaBoletoGateway;
    private final EventoBoletoGateway eventoBoletoGateway;

    public BoletoUseCaseConfig(
            BoletoGateway boletoGateway,
            CobrancaBoletoGateway cobrancaBoletoGateway,
            EventoBoletoGateway eventoBoletoGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaBoletoGateway = cobrancaBoletoGateway;
        this.eventoBoletoGateway = eventoBoletoGateway;
    }

    @Bean
    public CriaBoletoUseCase criaBoletoUseCase() {
        return new DefaultCriaBoletoUseCase(boletoGateway, eventoBoletoGateway);
    }

    @Bean
    public BuscaBoletoPorIdUseCase buscaBoletoPorIdUseCase() {
        return new DefaultBuscaBoletoPorIdUseCase(boletoGateway);
    }

    @Bean
    public ConfirmaRegistroPorIdUseCase confirmaRegistroPorIdUseCase() {
        return new DefaultConfirmaRegistroPorIdUseCase(boletoGateway, cobrancaBoletoGateway, eventoBoletoGateway);
    }

    @Bean
    public CriaPixBoletoUseCase criaPixBoletoUseCase() {
        return new DefaultCriaPixBoletoUseCase(boletoGateway, cobrancaBoletoGateway, eventoBoletoGateway);
    }
}
