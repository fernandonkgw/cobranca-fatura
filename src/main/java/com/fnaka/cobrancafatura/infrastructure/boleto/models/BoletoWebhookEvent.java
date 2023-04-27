package com.fnaka.cobrancafatura.infrastructure.boleto.models;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;

public record BoletoWebhookEvent(String id, BoletoStatus status) {
    public static BoletoWebhookEvent from(Boleto boleto) {
        return new BoletoWebhookEvent(boleto.getId().getValue(), boleto.getStatus());
    }
}
