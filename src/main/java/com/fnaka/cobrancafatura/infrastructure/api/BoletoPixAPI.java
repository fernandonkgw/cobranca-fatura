package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.boletopix.models.CriaBoletoRequest;
import com.fnaka.cobrancafatura.infrastructure.boletopix.models.CriaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boletopix.models.DetalhaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boletopix.models.CriaPixDeBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boletopix.models.ListaBoletosResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/boletos")
@Tag(name = "Boletos")
public interface BoletoPixAPI {

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
    CriaBoletoResponse criaBoleto(@RequestBody CriaBoletoRequest input);

    @GetMapping
    @Operation(summary = "Lista boletos registrados paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Filtro invalido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    Pagination<ListaBoletosResponse> listaBoletos();

    @PostMapping(value = "{id}/gerar-pix")
    @Operation(summary = "Gerar Pix de Boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Boleto nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    CriaPixDeBoletoResponse criaPixDeBoleto(@PathVariable(name = "id") String id);

    @GetMapping(value = "{id}")
    @Operation(summary = "Detalha um Boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleto encontrado"),
            @ApiResponse(responseCode = "404", description = "Boleto nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    DetalhaBoletoResponse detalhaBoleto(@PathVariable(name = "id") String id);
}
