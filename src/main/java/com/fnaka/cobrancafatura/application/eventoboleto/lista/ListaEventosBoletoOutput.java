package com.fnaka.cobrancafatura.application.eventoboleto.lista;

import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoleto;

import java.time.Instant;

public record ListaEventosBoletoOutput(
        String id,
        String boletoId,
        BoletoStatus status,
        Instant criadoEm,

        ListaRequisicaoEventoBoletoOutput requisicao
) {

    public static ListaEventosBoletoOutput from(EventoBoleto eventoBoleto) {

        return new ListaEventosBoletoOutput(
                eventoBoleto.getId().getValue(),
                eventoBoleto.getBoleto().getId().getValue(),
                eventoBoleto.getStatus(),
                eventoBoleto.getCriadoEm(),
                ListaRequisicaoEventoBoletoOutput.from(eventoBoleto.getRequisicao())
        );
    }
}