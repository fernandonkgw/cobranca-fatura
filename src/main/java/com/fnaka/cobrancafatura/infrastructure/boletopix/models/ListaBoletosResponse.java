package com.fnaka.cobrancafatura.infrastructure.boletopix.models;

import java.time.Instant;

public record ListaBoletosResponse(
        String id,
        Integer convenio,
        String numeroTituloCliente,
        String dataEmissao,
        String dataVencimento,
        Integer valorOriginal,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
