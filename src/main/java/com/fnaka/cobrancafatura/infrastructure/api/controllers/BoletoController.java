package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.api.BoletoAPI;
import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.ListaBoletosResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoController implements BoletoAPI {

    @Override
    public Pagination<ListaBoletosResponse> listaBoletos(String boletoErpId) {
        return null;
    }
}
