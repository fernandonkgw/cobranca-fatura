package com.fnaka.cobrancafatura.infrastructure.boleto.models;

public record CriaBoletoRequest(
        Integer convenio,
        String numeroTituloCliente
) {
}
