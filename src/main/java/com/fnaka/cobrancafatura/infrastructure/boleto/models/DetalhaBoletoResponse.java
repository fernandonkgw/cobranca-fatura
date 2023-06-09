package com.fnaka.cobrancafatura.infrastructure.boleto.models;

import com.fnaka.cobrancafatura.application.boleto.busca.BoletoOutput;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;

import java.time.Instant;

public record DetalhaBoletoResponse(
        String id,
        Integer convenio,
        String nossoNumero,
        BoletoStatus status,
        Instant criadoEm,
        Instant atualizadoEm,
        DetalhaPixBoletoResponse pix

) {
    public static DetalhaBoletoResponse from(BoletoOutput output) {
        return new DetalhaBoletoResponse(
                output.id(),
                output.convenio(),
                output.nossoNumero(),
                output.status(),
                output.criadoEm(),
                output.atualizadoEm(),
                DetalhaPixBoletoResponse.from(output.pix())
        );
    }
}
