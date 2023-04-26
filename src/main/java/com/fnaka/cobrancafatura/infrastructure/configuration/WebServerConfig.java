package com.fnaka.cobrancafatura.infrastructure.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fnaka.cobrancafatura")
@EnableFeignClients("com.fnaka.cobrancafatura.infrastructure.services.impl")
public class WebServerConfig {
}
