package com.fnaka.cobrancafatura.domain.eventoboleto;

import com.fnaka.cobrancafatura.domain.boleto.BoletoID;
import com.fnaka.cobrancafatura.domain.pagination.Pagination;

import java.util.List;

public interface EventoBoletoGateway {

    EventoBoleto create(EventoBoleto eventoBoleto);

    List<EventoBoleto> findByBoletoId(BoletoID boletoID);
}
