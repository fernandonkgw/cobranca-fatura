package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import feign.Logger;
import feign.Request;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class BancoBrasilRequestConfiguration {

    public Long CONNECT_TIMEOUT = 9_000L;
    public Long READ_TIMEOUT = 9_000L;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new BancoBrasilErrorDecoder();
    }

    @Bean
    public Request.Options getOptions() {
        return new Request.Options(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS, READ_TIMEOUT, TimeUnit.MILLISECONDS, false);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
