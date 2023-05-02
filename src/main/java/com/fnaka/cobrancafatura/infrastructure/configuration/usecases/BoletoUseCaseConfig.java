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
import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoletoUseCaseConfig {

    private final BoletoGateway boletoGateway;
    private final CobrancaGateway cobrancaGateway;
    private final EventoBoletoGateway eventoBoletoGateway;

    public BoletoUseCaseConfig(
            BoletoGateway boletoGateway,
            CobrancaGateway cobrancaGateway,
            EventoBoletoGateway eventoBoletoGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaGateway = cobrancaGateway;
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
        return new DefaultConfirmaRegistroPorIdUseCase(boletoGateway, cobrancaGateway);
    }

    @Bean
    public CriaPixBoletoUseCase criaPixBoletoUseCase() {
        return new DefaultCriaPixBoletoUseCase(boletoGateway, cobrancaGateway);
    }
}
