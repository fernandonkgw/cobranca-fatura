package com.fnaka.cobrancafatura.application.boleto.buscapix;

import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;

public record PixOutput(
        String url,
        String txId,
        String emv
) {

    public static PixOutput from(PixBoleto pixBoleto) {
        if (pixBoleto == null) {
            return null;
        }
        return new PixOutput(pixBoleto.url(), pixBoleto.txId(), pixBoleto.emv());
    }
}
