package com.fnaka.cobrancafatura.domain.dtos;

import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;
import com.fnaka.cobrancafatura.domain.eventoboleto.Requisicao;

public record PixBoletoRequisicao(
        PixBoleto pixBoleto,
        Requisicao requisicao
) {
}
