package com.fnaka.cobrancafatura.domain.boleto;

import java.util.Arrays;
import java.util.Optional;

public enum BoletoStatus {

    CRIADO(20),
    REGISTRADO(21),
    PIX_CRIADO(22),
    NAO_REGISTRADO(23),
    PIX_NAO_CRIADO(24);

    private final Integer codigo;

    BoletoStatus(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public static Optional<BoletoStatus> of(final Integer codigo) {
        return Arrays.stream(BoletoStatus.values())
                .filter(it -> it.codigo.equals(codigo))
                .findFirst();
    }
}
