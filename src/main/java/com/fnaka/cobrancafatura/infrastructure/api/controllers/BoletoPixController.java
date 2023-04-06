package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.api.BoletoPixAPI;
import com.fnaka.cobrancafatura.infrastructure.boletopix.models.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoPixController implements BoletoPixAPI {
    @Override
    public CriaBoletoResponse criaBoleto(CriaBoletoRequest input) {
        return null;
    }

    @Override
    public Pagination<ListaBoletosResponse> listaBoletos() {
        return null;
    }

    @Override
    public CriaPixDeBoletoResponse criaPixDeBoleto(String id) {
        return null;
    }

    @Override
    public DetalhaBoletoResponse detalhaBoleto(String id) {
        return null;
    }
}
