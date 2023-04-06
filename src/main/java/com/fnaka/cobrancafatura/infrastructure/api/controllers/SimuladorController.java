package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.infrastructure.api.SimuladorAPI;
import com.fnaka.cobrancafatura.infrastructure.simulador.models.CriaBoletoRequest;
import com.fnaka.cobrancafatura.infrastructure.simulador.models.CriaBoletoResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimuladorController implements SimuladorAPI {

    @Override
    public CriaBoletoResponse criaBoleto(CriaBoletoRequest input) {
        return null;
    }

    @Override
    public void pagaBoleto(String id) {

    }
}
