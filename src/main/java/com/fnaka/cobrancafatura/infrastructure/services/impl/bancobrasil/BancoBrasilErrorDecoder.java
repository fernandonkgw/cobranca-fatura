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
        final var message = response.reason();
        LOG.warn("Reason {}", response.reason());
        String responseBody;
        String url;
        String requestBody = null;

        try (InputStream bodyIs = response.body().asInputStream()) {

            final var request = response.request();
            url = request.url();
            byte[] body = request.body();
            if (body != null) {
                requestBody = new String(body, StandardCharsets.UTF_8);
            }
            responseBody = StreamUtils.copyToString(bodyIs, StandardCharsets.UTF_8);

        } catch (IOException e) {
            return new RuntimeException(message);
        }
        if (response.status() == 400 || response.status() == 404) {
            LOG.warn("Response: {}", responseBody);
            return new BadRequestException(message, responseBody, url, requestBody);
        }
        return new RuntimeException(response.toString());
    }
}