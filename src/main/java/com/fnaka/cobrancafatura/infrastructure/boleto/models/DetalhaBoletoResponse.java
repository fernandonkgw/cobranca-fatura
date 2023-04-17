package com.fnaka.cobrancafatura.infrastructure.boleto.models;

import com.fnaka.cobrancafatura.application.boleto.busca.BoletoOutput;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;

import java.time.Instant;

public record DetalhaBoletoResponse(
        String id,
        Integer convenio,
        String numeroTituloCliente,
        BoletoStatus status,
        Instant criadoEm,
        Instant atualizadoEm

) {
    public static DetalhaBoletoResponse from(BoletoOutput output) {
        return new DetalhaBoletoResponse(
                output.id(),
                output.convenio(),
                output.numeroTituloCliente(),
                output.status(),
                output.criadoEm(),
                output.atualizadoEm()
        );
    }
}
