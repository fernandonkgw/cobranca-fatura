package com.fnaka.cobrancafatura.infrastructure.simulador.models;

public record CriaBoletoRequest(
        Integer convenio,
        String numeroTituloCliente
) {
}
