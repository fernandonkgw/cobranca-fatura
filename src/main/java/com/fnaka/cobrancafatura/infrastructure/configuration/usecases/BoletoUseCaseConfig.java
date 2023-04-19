package com.fnaka.cobrancafatura.infrastructure.configuration.usecases;

import com.fnaka.cobrancafatura.application.boleto.busca.BuscaBoletoPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.busca.DefaultBuscaBoletoPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.confirmaregistro.ConfirmaRegistroPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.confirmaregistro.DefaultConfirmaRegistroPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.DefaultCriaBoletoUseCase;
import com.fnaka.cobrancafatura.domain.boleto.BoletoGateway;
import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoletoUseCaseConfig {

    private final BoletoGateway boletoGateway;
    private final CobrancaGateway cobrancaGateway;

    public BoletoUseCaseConfig(
            BoletoGateway boletoGateway, CobrancaGateway cobrancaGateway
    ) {
        this.boletoGateway = boletoGateway;
        this.cobrancaGateway = cobrancaGateway;
    }

    @Bean
    public CriaBoletoUseCase criaBoletoUseCase() {
        return new DefaultCriaBoletoUseCase(boletoGateway);
    }

    @Bean
    public BuscaBoletoPorIdUseCase buscaBoletoPorIdUseCase() {
        return new DefaultBuscaBoletoPorIdUseCase(boletoGateway);
    }

    @Bean
    public ConfirmaRegistroPorIdUseCase confirmaRegistroPorIdUseCase() {
        return new DefaultConfirmaRegistroPorIdUseCase(boletoGateway, cobrancaGateway);
    }
}
