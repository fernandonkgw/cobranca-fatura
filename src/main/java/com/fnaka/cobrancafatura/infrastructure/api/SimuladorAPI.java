package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.infrastructure.simulador.models.CriaBoletoRequest;
import com.fnaka.cobrancafatura.infrastructure.simulador.models.CriaBoletoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/simulador")
@Tag(name = "Simulador", description = "Nao disponivel em ambiente de producao")
public interface SimuladorAPI {

    @PostMapping(value = "boletos")
    @Operation(summary = "Cria um novo Boleto e encaminha para fila para registro no Banco do Brasil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Um erro de validacao foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    CriaBoletoResponse criaBoleto(CriaBoletoRequest input);

    @PostMapping(value = "boletos/{id}/paga")
    @Operation(summary = "Encaminha para fila para registrar no Banco do Brasil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Um erro de validacao foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    void pagaBoleto(@PathVariable(name = "id") String id);
}
