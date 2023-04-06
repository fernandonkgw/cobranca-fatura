package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.api.BoletoAPI;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaPixDeBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaPixDoBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.ListaBoletosResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoController implements BoletoAPI {

    @Override
    public Pagination<ListaBoletosResponse> listaBoletos(String boletoErpId) {
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

    @Override
    public DetalhaPixDoBoletoResponse detalhaPixDoBoleto(String id) {
        return null;
    }
}
