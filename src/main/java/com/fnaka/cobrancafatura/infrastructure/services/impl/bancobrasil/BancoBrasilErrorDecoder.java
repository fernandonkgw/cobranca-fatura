package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.infrastructure.services.BadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BancoBrasilErrorDecoder implements ErrorDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(BancoBrasilErrorDecoder.class);
    @Override
    public Exception decode(String s, Response response) {
        LOG.warn("Response {}", response.reason());
        String message;

        try (InputStream bodyIs = response.body().asInputStream()) {

            message = StreamUtils.copyToString(bodyIs, StandardCharsets.UTF_8);

        } catch (IOException e) {
            return new RuntimeException(response.reason());
        }
        if (response.status() == 400 || response.status() == 404) {
            LOG.warn("Response: {}", message);
            return new BadRequestException(message);
        }
        return new RuntimeException(response.toString());
    }
}