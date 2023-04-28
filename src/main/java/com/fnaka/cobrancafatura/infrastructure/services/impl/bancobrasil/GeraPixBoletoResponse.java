package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeraPixBoletoResponse(
        @JsonProperty("pix.chave") String chave,
        @JsonProperty("qrCode.url") String url,
        @JsonProperty("qrCode.txId") String txId,
        @JsonProperty("qrCode.emv") String emv
) {
}

