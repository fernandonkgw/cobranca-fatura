package com.fnaka.cobrancafatura.infrastructure.boletopix.models;

public record CriaBoletoRequest(
        Integer convenio,
        String numeroTituloCliente
) {
}
