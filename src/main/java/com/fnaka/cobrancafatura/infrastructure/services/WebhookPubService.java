package com.fnaka.cobrancafatura.infrastructure.services;

import com.fnaka.cobrancafatura.infrastructure.boleto.models.BoletoWebhookEvent;

public interface WebhookPubService {

    void send(BoletoWebhookEvent boletoWebhookEvent);

}
