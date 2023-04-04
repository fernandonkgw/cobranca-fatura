package com.fnaka.cobrancafatura.infrastructure.api;

import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.ListaBoletosResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


}
