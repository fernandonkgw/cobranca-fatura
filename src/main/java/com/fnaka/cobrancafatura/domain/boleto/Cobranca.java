package com.fnaka.cobrancafatura.domain.boleto;

public record Cobranca(
        String codigoLinhaDigitavel,
        Integer codigoCanalPagamento,
        Integer codigoEstadoTituloCobranca,
        Integer valorPagoSacado
) {
}
