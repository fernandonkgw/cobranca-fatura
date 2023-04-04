package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.infrastructure.api.BoletoErpAPI;
import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.BuscaBoletoErpPorIdResponse;
import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.CriaBoletoErpRequest;
import com.fnaka.cobrancafatura.infrastructure.boletoerp.models.CriaBoletoErpResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoErpController implements BoletoErpAPI {
    @Override
    public CriaBoletoErpResponse criaBoletoErp(CriaBoletoErpRequest input) {
        return null;
    }

    @Override
    public BuscaBoletoErpPorIdResponse buscaPorId(String id) {
        return null;
    }
}
