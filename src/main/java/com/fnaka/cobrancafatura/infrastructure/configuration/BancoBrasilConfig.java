package com.fnaka.cobrancafatura.infrastructure.configuration;

import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil.CobrancaBoletoClientService;
import com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil.CobrancaBoletoFeignClient;
import com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil.OAuthClientService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BancoBrasilConfig {

    @Bean
    @ConfigurationProperties("banco-brasil.credential")
    public BancoBrasilCredential bancoBrasilCredential() {
        return new BancoBrasilCredential();
    }

    @Bean
    CobrancaBoletoService cobrancaBoletoService(
            OAuthClientService oAuthClientService,
            BancoBrasilCredential bancoBrasilCredential,
            CobrancaBoletoFeignClient cobrancaBoletoFeignClient
    ) {
        return new CobrancaBoletoClientService(oAuthClientService, bancoBrasilCredential, cobrancaBoletoFeignClient);
    }
}
