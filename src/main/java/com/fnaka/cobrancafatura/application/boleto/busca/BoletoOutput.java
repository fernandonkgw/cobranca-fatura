package com.fnaka.cobrancafatura.application.boleto.busca;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;

import java.time.Instant;

public record BoletoOutput(
        String id,
        Integer convenio,
        String numeroTituloCliente,
        BoletoStatus status,
        Instant criadoEm,
        Instant atualizadoEm
) {

    public static BoletoOutput from(Boleto boleto) {
        return new BoletoOutput(
                boleto.getId().getValue(),
                boleto.getConvenio(),
                boleto.getNumeroTituloCliente(),
                boleto.getStatus(),
                boleto.getCriadoEm(),
                boleto.getAtualizadoEm()
        );
    }


}
