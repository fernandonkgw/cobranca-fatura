package com.fnaka.cobrancafatura.infrastructure.eventoboleto.models;

import com.fnaka.cobrancafatura.application.eventoboleto.lista.ListaEventosBoletoOutput;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;

import java.time.Instant;

public record ListaEventosBoletoResponse(
        String id,
        String boletoId,
        BoletoStatus status,
        Instant criadoEm,
        ListaRequisicaoEventoBoletoResponse requisicao
) {

    public static ListaEventosBoletoResponse from(ListaEventosBoletoOutput output) {
        return new ListaEventosBoletoResponse(
                output.id(),
                output.boletoId(),
                output.status(),
                output.criadoEm(),
                ListaRequisicaoEventoBoletoResponse.from(output.requisicao())
        );
    }
}
