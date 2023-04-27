package com.fnaka.cobrancafatura.infrastructure.configuration;

import com.fnaka.cobrancafatura.infrastructure.services.WebhookPubService;
import com.fnaka.cobrancafatura.infrastructure.services.local.InMemoryWebhookPubService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebhookPubConfig {

    @Bean
    WebhookPubService inMemoryWebhookService() {
        return new InMemoryWebhookPubService();
    }
}
