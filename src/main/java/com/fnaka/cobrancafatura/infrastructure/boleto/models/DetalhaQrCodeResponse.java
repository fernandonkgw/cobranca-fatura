package com.fnaka.cobrancafatura.infrastructure.boleto.models;

public record DetalhaQrCodeResponse(
        String url,
        String txId,
        String emv
) {
}
