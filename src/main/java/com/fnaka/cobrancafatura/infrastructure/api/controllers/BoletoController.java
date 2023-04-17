package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.application.boleto.busca.BuscaBoletoPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoCommand;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoUseCase;
import com.fnaka.cobrancafatura.infrastructure.api.BoletoAPI;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaBoletoRequest;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaBoletoResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoController implements BoletoAPI {

    private final CriaBoletoUseCase criaBoletoUseCase;
    private final BuscaBoletoPorIdUseCase buscaBoletoPorIdUseCase;

    public BoletoController(
            CriaBoletoUseCase criaBoletoUseCase,
            BuscaBoletoPorIdUseCase buscaBoletoPorIdUseCase
    ) {
        this.criaBoletoUseCase = criaBoletoUseCase;
        this.buscaBoletoPorIdUseCase = buscaBoletoPorIdUseCase;
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
    public DetalhaBoletoResponse detalhaBoleto(String id) {
        return DetalhaBoletoResponse.from(this.buscaBoletoPorIdUseCase.execute(id));
    }
}
