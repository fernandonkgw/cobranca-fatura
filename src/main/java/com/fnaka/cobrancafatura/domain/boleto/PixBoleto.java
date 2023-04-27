package com.fnaka.cobrancafatura.domain.boleto;

public record PixBoleto(
        String url,
        String txId,
        String emv
) {
}
