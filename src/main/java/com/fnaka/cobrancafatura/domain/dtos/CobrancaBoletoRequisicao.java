package com.fnaka.cobrancafatura.domain.dtos;

import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoleto;
import com.fnaka.cobrancafatura.domain.eventoboleto.Requisicao;

public record CobrancaBoletoRequisicao(
        CobrancaBoleto cobrancaBoleto,
        Requisicao requisicao
) {

}
