package com.fnaka.cobrancafatura.infrastructure.boletoerp.models;

public record CriaBoletoErpRequest(
        Integer convenio,
        String numeroTituloCliente
) {
}
