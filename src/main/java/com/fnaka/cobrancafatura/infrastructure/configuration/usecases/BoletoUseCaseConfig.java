package com.fnaka.cobrancafatura.infrastructure.configuration.usecases;

import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.DefaultCriaBoletoUseCase;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoletoUseCaseConfig {

    private final BoletoGateway boletoGateway;

    public BoletoUseCaseConfig(BoletoGateway boletoGateway) {
        this.boletoGateway = boletoGateway;
    }

    @Bean
    public CriaBoletoUseCase criaBoletoUseCase() {
        return new DefaultCriaBoletoUseCase(boletoGateway);
    }
}
