package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.infrastructure.eventoboleto.models.ListaEventosBoletoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("v1/boletos/{boletoId}/eventos")
@Tag(name = "Eventos de Boletos")
public interface EventoBoletoAPI {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista eventos de um boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos do boleto retornados"),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor")
    })
    List<ListaEventosBoletoResponse> listaEventosBoletos(@PathVariable(name = "boletoId") String boletoId);
}
