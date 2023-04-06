package com.fnaka.cobrancafatura.infrastructure.boleto.models;

import com.fnaka.cobrancafatura.domain.boleto.PixBoletoStatus;

import java.time.Instant;

public record DetalhaPixDoBoletoResponse(
        String id,
        PixBoletoStatus status,

        String motivoErro,
        Instant criadoEm,
        Instant atualizadoEm,

        DetalhaQrCodeResponse qrCode
) {
}
