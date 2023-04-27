package com.fnaka.cobrancafatura.application.boleto.criapix;

import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;

public record CriaPixBoletoOutput(
        String url,
        String txId,
        String emv
) {
    public static CriaPixBoletoOutput from(PixBoleto pix) {
        return new CriaPixBoletoOutput(
                pix.url(),
                pix.txId(),
                pix.emv()
        );
    }
}
