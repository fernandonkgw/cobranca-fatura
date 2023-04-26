package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.utils.AuthorizationUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OAuthClientService {

    private final OAuthFeignClient oAuthFeignClient;
    private final BancoBrasilCredential bancoBrasilCredential;

    public OAuthClientService(
            OAuthFeignClient oAuthFeignClient, BancoBrasilCredential bancoBrasilCredential
    ) {
        this.oAuthFeignClient = oAuthFeignClient;
        this.bancoBrasilCredential = bancoBrasilCredential;
    }

    public Token generateToken() {
        final var username = bancoBrasilCredential.getClientId();
        final var password = bancoBrasilCredential.getClientSecret();
        final var authorization = AuthorizationUtils.basicAuth(username, password);

        final var formData = new HashMap<String, String>();
        formData.put("grant_type", "client_credentials");

        return oAuthFeignClient.token(authorization, formData);
    }
}
