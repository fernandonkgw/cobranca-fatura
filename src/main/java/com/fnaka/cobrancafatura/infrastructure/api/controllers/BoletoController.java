package com.fnaka.cobrancafatura.infrastructure.api.controllers;

import com.fnaka.cobrancafatura.application.boleto.busca.BuscaBoletoPorIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.buscapix.BuscaPixPorBoletoIdUseCase;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoCommand;
import com.fnaka.cobrancafatura.application.boleto.cria.CriaBoletoUseCase;
import com.fnaka.cobrancafatura.infrastructure.api.BoletoAPI;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaBoletoRequest;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.CriaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaBoletoResponse;
import com.fnaka.cobrancafatura.infrastructure.boleto.models.DetalhaPixBoletoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoController implements BoletoAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoletoController.class);

    private final CriaBoletoUseCase criaBoletoUseCase;
    private final BuscaBoletoPorIdUseCase buscaBoletoPorIdUseCase;
    private final BuscaPixPorBoletoIdUseCase buscaPixPorBoletoIdUseCase;

    public BoletoController(
            CriaBoletoUseCase criaBoletoUseCase,
            BuscaBoletoPorIdUseCase buscaBoletoPorIdUseCase,
            BuscaPixPorBoletoIdUseCase buscaPixPorBoletoIdUseCase
    ) {
        this.criaBoletoUseCase = criaBoletoUseCase;
        this.buscaBoletoPorIdUseCase = buscaBoletoPorIdUseCase;
        this.buscaPixPorBoletoIdUseCase = buscaPixPorBoletoIdUseCase;
    }

    @Override
    public CriaBoletoResponse criaBoleto(CriaBoletoRequest input) {
        final var command = CriaBoletoCommand.with(
                input.convenio(), input.nossoNumero()
        );

        final var output = this.criaBoletoUseCase.execute(command);
        LOGGER.info("Fim da execucao de criacao de boleto");

        return CriaBoletoResponse.from(output);
    }

    @Override
    public DetalhaBoletoResponse detalhaBoleto(String id) {
        return DetalhaBoletoResponse.from(this.buscaBoletoPorIdUseCase.execute(id));
    }

    @Override
    public DetalhaPixBoletoResponse buscaPixBoleto(String id) {
        return DetalhaPixBoletoResponse.from(this.buscaPixPorBoletoIdUseCase.execute(id));
    }
}
