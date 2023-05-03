package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoleto;

public record CobrancaResponse(
        String codigoLinhaDigitavel,
        Integer codigoCanalPagamento,
        Integer codigoEstadoTituloCobranca,
        Integer valorPagoSacado
) {

    public CobrancaBoleto toDomain() {
        return new CobrancaBoleto(
                codigoLinhaDigitavel,
                codigoCanalPagamento,
                codigoEstadoTituloCobranca,
                valorPagoSacado
        );
    }
}
