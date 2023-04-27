package com.fnaka.cobrancafatura.infrastructure.configuration;

import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil.CobrancaClientBoletoService;
import com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil.OAuthFeignClient;
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
            OAuthFeignClient oAuthFeignClient, BancoBrasilCredential bancoBrasilCredential
    ) {
        return new CobrancaClientBoletoService(oAuthFeignClient, bancoBrasilCredential);
    }
}
