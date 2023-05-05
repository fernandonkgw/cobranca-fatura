package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaBoletoRequest;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaPixBoletoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/boletos")
@Tag(name = "Boletos")
public interface BoletoAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cria um novo Boleto ERP para importar um Boleto Registrado na instituicao financeira",
            description = "Notifica Correios Empresa com webhook"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Um erro de validacao foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    CriaBoletoResponse criaBoleto(@RequestBody CriaBoletoRequest input);

    @GetMapping(value = "{id}")
    @Operation(summary = "Detalha um Boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleto encontrado"),
            @ApiResponse(responseCode = "404", description = "Boleto nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    DetalhaBoletoResponse detalhaBoleto(@PathVariable(name = "id") String id);

    @GetMapping(value = "{id}/pix")
    @Operation(summary = "Detalha um Pix do Boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pix do Boleto encontrado"),
            @ApiResponse(responseCode = "400", description = "Pix nao gerado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    DetalhaPixBoletoResponse buscaPixBoleto(@PathVariable(name = "id") String id);
}
