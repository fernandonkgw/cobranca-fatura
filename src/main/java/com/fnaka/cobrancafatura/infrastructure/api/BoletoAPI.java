package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaPixDeBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaPixDoBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.ListaBoletosResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/boletos")
@Tag(name = "Boletos")
public interface BoletoAPI {

    @GetMapping
    @Operation(summary = "Lista boletos registrados paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Filtro invalido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    Pagination<ListaBoletosResponse> listaBoletos(
            @RequestParam(name = "boletoErpId") String boletoErpId
    );

    @PostMapping(value = "{id}/gerar-pix")
    @Operation(summary = "Gerar Pix de Boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Boleto nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    CriaPixDeBoletoResponse criaPixDeBoleto(@PathVariable(name = "id") String id);

    @PostMapping(value = "{id}")
    @Operation(summary = "Detalha um Boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleto encontrado"),
            @ApiResponse(responseCode = "404", description = "Boleto nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    DetalhaBoletoResponse detalhaBoleto(@PathVariable(name = "id") String id);

    @PostMapping(value = "{id}/pix")
    @Operation(summary = "Detalha Pix do Boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleto encontrado"),
            @ApiResponse(responseCode = "404", description = "Boleto nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    })
    DetalhaPixDoBoletoResponse detalhaPixDoBoleto(@PathVariable(name = "id") String id);
}
