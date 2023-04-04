package com.fnaka.cobrancafatura.infrastructure.boletoerp.models;

import com.fnaka.cobrancafatura.domain.boletoerp.BoletoErpStatus;

import java.time.Instant;

public record BuscaBoletoErpPorIdResponse(
        String id,
        Integer convenio,
        String numeroTituloCliente,
        BoletoErpStatus status,
        String mensagem,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
