package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoCommand;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoUseCase;
import com.fnaka.cobrancafatura.domain.pagination.Pagination;
import com.fnaka.cobrancafatura.infrastructure.api.BoletoAPI;
import com.fnaka.cobrancafatura.infrastructure.boletopix.models.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoController implements BoletoAPI {

    private final CriaBoletoUseCase criaBoletoUseCase;

    public BoletoController(CriaBoletoUseCase criaBoletoUseCase) {
        this.criaBoletoUseCase = criaBoletoUseCase;
    }

    @Override
    public CriaBoletoResponse criaBoleto(CriaBoletoRequest input) {
        final var command = CriaBoletoCommand.with(
                input.convenio(), input.numeroTituloCliente()
        );

        final var output = this.criaBoletoUseCase.execute(command);

        return CriaBoletoResponse.from(output);
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
