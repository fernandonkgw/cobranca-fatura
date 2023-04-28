package com.fnaka.cobrancafatura.application.boleto.cria;

public record CriaBoletoCommand(
        Integer convenio,
        String nossoNumero
) {
    public static CriaBoletoCommand with(final Integer convenio, final String nossoNumero) {
        return new CriaBoletoCommand(convenio, nossoNumero);
    }
}
