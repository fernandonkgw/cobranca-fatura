package com.fnaka.cobrancafatura.application.eventoboleto.notifica;

public record NotificaEventoBoletoOutput(
        String id,
        String boletoId,
        String eventoWebhookId
) {
}
