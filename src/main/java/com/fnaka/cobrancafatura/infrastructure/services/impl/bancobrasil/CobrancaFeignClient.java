package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "bancoBrasilCobrancaClient",
        url = "https://api.sandbox.bb.com.br/cobrancas",
        configuration = BancoBrasilRequestConfiguration.class
)
public interface CobrancaFeignClient {

    @GetMapping(
            value = "v2/boletos/{numeroTituloCliente}",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CobrancaResponse detalhaBoleto(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("numeroTituloCliente") String numeroTituloCliente,
            @RequestParam("gw-dev-app-key") String devAppKey,
            @RequestParam("numeroConvenio") String numeroConvenio
    );
}
