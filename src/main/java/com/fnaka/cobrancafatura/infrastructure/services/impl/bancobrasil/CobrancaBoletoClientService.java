package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import com.fnaka.cobrancafatura.infrastructure.utils.AuthorizationUtils;

import java.util.HashMap;

public class CobrancaBoletoClientService implements CobrancaBoletoService {

    private final OAuthClientService oAuthClientService;
    private final BancoBrasilCredential bancoBrasilCredential;
    private final CobrancaBoletoFeignClient cobrancaBoletoFeignClient;

    public CobrancaBoletoClientService(
            OAuthClientService oAuthClientService,
            BancoBrasilCredential bancoBrasilCredential,
            CobrancaBoletoFeignClient cobrancaBoletoFeignClient) {
        this.oAuthClientService = oAuthClientService;
        this.bancoBrasilCredential = bancoBrasilCredential;
        this.cobrancaBoletoFeignClient = cobrancaBoletoFeignClient;
    }

    @Override
    public Cobranca detalhaCobrancaBoleto(Integer convenio, String numeroTituloCliente) {
        final var token = oAuthClientService.generateToken();
        final var bearerToken = token.getBearerToken();
        final var developerApplicationKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var cobrancaResponse = cobrancaBoletoFeignClient.detalhaBoleto(bearerToken, numeroTituloCliente, developerApplicationKey, convenio);

        return cobrancaResponse.toDomain();
    }
}