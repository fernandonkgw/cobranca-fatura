package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.infrastructure.api.WebhookAPI;
import com.fnaka.cobrancafatura.infrastructure.webhook.models.RecebeNotificacaoPagamentoRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController implements WebhookAPI {

    @Override
    public void recebeNotificacao(RecebeNotificacaoPagamentoRequest input) {

    }
}
