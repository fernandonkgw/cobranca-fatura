package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.BuscaBoletoErpPorIdResponse;
import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.CriaBoletoErpRequest;
import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.CriaBoletoErpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/boletos-erp")
@Tag(name = "Boletos ERP")
public interface BoletoErpAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Cria um novo Boleto ERP para importar um Boleto Registrado na instituicao financeira",
            description = "Notifica Correios Empresa com webhook"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Um erro de validacao foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    CriaBoletoErpResponse criaBoletoErp(@RequestBody CriaBoletoErpRequest input);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Busca Boleto ERP pelo identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleto ERP recuperado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Boleto ERO nao foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    BuscaBoletoErpPorIdResponse buscaPorId(@PathVariable String id);
}
