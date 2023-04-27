package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.domain.boleto.Cobranca;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import com.fnaka.cobrancafatura.infrastructure.utils.AuthorizationUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

public class CobrancaClientBoletoService implements CobrancaBoletoService {

    private final OAuthFeignClient oAuthFeignClient;
    private final BancoBrasilCredential bancoBrasilCredential;

    public CobrancaClientBoletoService(
            OAuthFeignClient oAuthFeignClient, BancoBrasilCredential bancoBrasilCredential
    ) {
        this.oAuthFeignClient = oAuthFeignClient;
        this.bancoBrasilCredential = bancoBrasilCredential;
    }

    @Override
    public Cobranca detalhaCobrancaBoleto(Integer convenio, String numeroTituloCliente) {
        final var username = bancoBrasilCredential.getClientId();
        final var password = bancoBrasilCredential.getClientSecret();
        final var authorization = AuthorizationUtils.basicAuth(username, password);
        final var formData = new HashMap<String, String>();
        formData.put("grant_type", "client_credentials");
        final var token = oAuthFeignClient.token(authorization, formData);



        return null;
    }
}
