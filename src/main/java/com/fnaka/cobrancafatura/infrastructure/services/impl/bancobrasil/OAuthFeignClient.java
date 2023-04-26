package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(
        name = "bancoBrasilOAuthClient",
        url = "https://oauth.sandbox.bb.com.br/oauth/token",
        configuration = BancoBrasilRequestConfiguration.class
)
public interface OAuthFeignClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Token token(
            @RequestHeader("Authorization") String basicAuth,
            Map<String, ?> formData
    );
}
