package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import feign.Response;
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
    Response findByNossoNumeroAndConvenio(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("gw-dev-app-key") String devAppKey,
            @PathVariable("id") String nossoNumero,
            @RequestParam("numeroConvenio") Integer convenio
    );

    @PostMapping(
            value = "v2/boletos/{id}/gerar-pix",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Response createPixBoleto(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("gw-dev-app-key") String devAppKey,
            @PathVariable("id") String nossoNumero,
            @RequestBody GeraPixBoletoRequest request
    );
}
