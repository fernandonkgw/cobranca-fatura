package com.fnaka.cobrancafatura.domain.boleto;

public record CobrancaBoleto(
        String codigoLinhaDigitavel,
        Integer codigoCanalPagamento,
        Integer codigoEstadoTituloCobranca,
        Integer valorPagoSacado
) {
}
