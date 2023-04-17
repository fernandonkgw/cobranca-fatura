package com.fnaka.cobrancafatura.infrastructure.boleto.models;

import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoOutput;

public record CriaBoletoResponse(
        String id
) {

    public static CriaBoletoResponse from(CriaBoletoOutput output) {
        return new CriaBoletoResponse(output.id());
    }
}
