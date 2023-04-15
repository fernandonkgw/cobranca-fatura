package com.fnaka.cobrancafatura.application.boleto.cria;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;

public record CriaBoletoOutput(String id) {

    public static CriaBoletoOutput from(final Boleto boleto) {
        return new CriaBoletoOutput(boleto.getId().getValue());
    }
}
