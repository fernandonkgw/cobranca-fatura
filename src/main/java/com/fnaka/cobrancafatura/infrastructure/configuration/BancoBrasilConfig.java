package com.fnaka.cobrancafatura.infrastructure.configuration;

import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
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
}
