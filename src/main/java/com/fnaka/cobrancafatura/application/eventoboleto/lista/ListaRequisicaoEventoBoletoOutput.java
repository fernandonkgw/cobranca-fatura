package com.fnaka.cobrancafatura.application.eventoboleto.lista;

import com.fnaka.cobrancafatura.domain.eventoboleto.Requisicao;

public record ListaRequisicaoEventoBoletoOutput(
        String url,
        String payloadRequest,
        String payloadResponse,
        Long responseTime
) {

    public static ListaRequisicaoEventoBoletoOutput from(Requisicao requisicao) {
        if (requisicao == null) return null;
        return new ListaRequisicaoEventoBoletoOutput(
                requisicao.url(),
                requisicao.payloadRequest(),
                requisicao.payloadResponse(),
                requisicao.responseTime()
        );
    }
}
