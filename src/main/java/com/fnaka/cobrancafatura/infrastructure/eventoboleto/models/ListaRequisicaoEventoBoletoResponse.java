package com.fnaka.cobrancafatura.infrastructure.eventoboleto.models;

import com.fnaka.cobrancafatura.application.eventoboleto.lista.ListaRequisicaoEventoBoletoOutput;

public record ListaRequisicaoEventoBoletoResponse(
        String url,
        String payloadRequest,
        String payloadResponse,
        Long responseTime
) {

    public static ListaRequisicaoEventoBoletoResponse from(ListaRequisicaoEventoBoletoOutput output) {
        if (output == null) return null;
        return new ListaRequisicaoEventoBoletoResponse(
                output.url(),
                output.payloadRequest(),
                output.payloadResponse(),
                output.responseTime()
        );
    }
}
