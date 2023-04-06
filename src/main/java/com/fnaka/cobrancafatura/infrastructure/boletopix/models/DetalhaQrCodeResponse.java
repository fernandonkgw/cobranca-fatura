package com.fnaka.cobrancafatura.infrastructure.boletopix.models;

public record DetalhaQrCodeResponse(
        String url,
        String txId,
        String emv
) {
}
