package com.fnaka.cobrancafatura.infrastructure.services.local;

import com.fnaka.cobrancafatura.infrastructure.boleto.models.BoletoWebhookEvent;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import com.fnaka.cobrancafatura.infrastructure.services.WebhookPubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryWebhookPubService implements WebhookPubService {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryWebhookPubService.class);

    @Override
    public void send(BoletoWebhookEvent webhookEvent) {
        LOG.info("Webhook event was sent: {}", Json.writeValueAsString(webhookEvent));
    }
}
