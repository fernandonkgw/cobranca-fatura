package com.fnaka.cobrancafatura.domain.eventoboleto;

public record Requisicao(
        String url,
        String payloadRequest,
        String payloadResponse
) {
}
