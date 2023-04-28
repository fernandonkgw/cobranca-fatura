package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "bancoBrasilCobrancaBoletoClient",
        url = "https://api.sandbox.bb.com.br/cobrancas",
        configuration = BancoBrasilRequestConfiguration.class
)
public interface CobrancaBoletoFeignClient {

    @GetMapping(
            value = "v2/boletos/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CobrancaResponse detalhaBoleto(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("id") String numeroTituloCliente,
            @RequestParam("gw-dev-app-key") String devAppKey,
            @RequestParam("numeroConvenio") Integer numeroConvenio
    );

    @PostMapping(
            value = "v2/boletos/{id}/gerar-pix",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    GeraPixBoletoResponse geraPixBoleto(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("gw-dev-app-key") String devAppKey,
            @PathVariable("id") String nossoNumero,
            @RequestBody GeraPixBoletoRequest request
    );
}
