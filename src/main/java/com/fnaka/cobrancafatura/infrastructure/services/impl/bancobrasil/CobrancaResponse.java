package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;

public record CobrancaResponse(
        String codigoLinhaDigitavel,
        Integer codigoCanalPagamento,
        Integer codigoEstadoTituloCobranca,
        Integer valorPagoSacado
) {

    public Cobranca toDomain() {
        return new Cobranca(
                codigoLinhaDigitavel,
                codigoCanalPagamento,
                codigoEstadoTituloCobranca,
                valorPagoSacado
        );
    }
}
