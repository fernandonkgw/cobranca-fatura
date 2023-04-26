package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

public record CobrancaResponse(
        String codigoLinhaDigitavel,
        Integer codigoCanalPagamento,
        Integer codigoEstadoTituloCobranca,
        Integer valorPagoSacado
) {
}
