package com.fnaka.cobrancafatura.application.boleto.cria;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoID;

public record CriaBoletoOutput(String id) {

    public static CriaBoletoOutput from(BoletoID id) {
        return new CriaBoletoOutput(id.getValue());
    }

    public static CriaBoletoOutput from(final Boleto boleto) {
        return from(boleto.getId());
    }
}
