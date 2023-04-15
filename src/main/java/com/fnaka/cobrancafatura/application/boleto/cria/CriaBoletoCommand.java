package com.fnaka.cobrancafatura.application.boleto.cria;

public record CriaBoletoCommand(
        Integer convenio,
        String numeroTituloCliente
) {
    public static CriaBoletoCommand with(final Integer convenio, final String numeroTituloCliente) {
        return new CriaBoletoCommand(convenio, numeroTituloCliente);
    }
}
