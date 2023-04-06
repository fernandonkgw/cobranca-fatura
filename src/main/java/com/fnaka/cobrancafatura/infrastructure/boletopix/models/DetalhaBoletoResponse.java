package com.fnaka.cobrancafatura.infrastructure.boletopix.models;

import java.time.Instant;

public record DetalhaBoletoResponse(
        String id,
        Integer convenio,
        String numeroTituloCliente,

        Instant dataHoraEmissao,

        Instant dataVencimento,

        Instant dataPagamento,

        String mensagem,
        Instant criadoEm,
        Instant atualizadoEm,

        DetalhaQrCodeResponse qrCode
) {
}
