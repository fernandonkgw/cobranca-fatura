package com.fnaka.cobrancafatura.infrastructure.boleto.models;

import com.fnaka.cobrancafatura.application.boleto.busca.PixOutput;

public record DetalhaPixBoletoResponse(String url, String txId, String emv) {

    public static DetalhaPixBoletoResponse from(PixOutput output) {
        if (output == null) {
            return null;
        }
        return new DetalhaPixBoletoResponse(output.url(), output.txId(), output.emv());
    }
}
