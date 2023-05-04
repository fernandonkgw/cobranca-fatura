package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.application.eventoboleto.lista.ListaEventosBoletoUseCase;
import com.fnaka.cobrancafatura.infrastructure.api.EventoBoletoAPI;
import com.fnaka.cobrancafatura.infrastructure.eventoboleto.models.ListaEventosBoletoResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventoBoletoController implements EventoBoletoAPI {

    private final ListaEventosBoletoUseCase listaEventosBoletoUseCase;

    public EventoBoletoController(ListaEventosBoletoUseCase listaEventosBoletoUseCase) {
        this.listaEventosBoletoUseCase = listaEventosBoletoUseCase;
    }

    @Override
    public List<ListaEventosBoletoResponse> listaEventosBoletos(String boletoId) {
        return this.listaEventosBoletoUseCase.execute(boletoId)
                .stream()
                .map(ListaEventosBoletoResponse::from)
                .toList();
    }
}
