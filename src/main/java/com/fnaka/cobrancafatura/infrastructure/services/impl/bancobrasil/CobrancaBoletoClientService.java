package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.domain.dtos.CobrancaBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.dtos.PixBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.eventoboleto.Requisicao;
import com.fnaka.cobrancafatura.domain.utils.InstantUtils;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CobrancaBoletoClientService implements CobrancaBoletoService {

    private final OAuthClientService oAuthClientService;
    private final BancoBrasilCredential bancoBrasilCredential;
    private final CobrancaBoletoFeignClient cobrancaBoletoFeignClient;

    public CobrancaBoletoClientService(
            OAuthClientService oAuthClientService,
            BancoBrasilCredential bancoBrasilCredential,
            CobrancaBoletoFeignClient cobrancaBoletoFeignClient
    ) {
        this.oAuthClientService = oAuthClientService;
        this.bancoBrasilCredential = bancoBrasilCredential;
        this.cobrancaBoletoFeignClient = cobrancaBoletoFeignClient;
    }

    @Override
    public CobrancaBoletoRequisicao findByNossoNumeroAndConvenio(String nossoNumero, Integer convenio) {
        final var bearerToken = getBearerToken();
        final var developerApplicationKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var requestedAt = InstantUtils.now();
        final var response = cobrancaBoletoFeignClient.findByNossoNumeroAndConvenio(
                bearerToken,
                developerApplicationKey,
                nossoNumero,
                convenio
        );
        final var tempoResponse = InstantUtils.durationUntilNow(requestedAt);

        final var httpStatus = httpStatusFrom(response);
        final var url = urlFrom(response);
        final var requestBody = requestBodyFrom(response);
        final var responseBody = responseBodyFrom(response);
        final var cobrancaResponse = readValue(httpStatus, responseBody, CobrancaResponse.class);
        final var requisicao = new Requisicao(url, requestBody, responseBody, tempoResponse);
        final var cobrancaBoleto = cobrancaResponse != null ? cobrancaResponse.toDomain() :null;

        return new CobrancaBoletoRequisicao(cobrancaBoleto, requisicao);
    }

    @Override
    public PixBoletoRequisicao createPix(String nossoNumero, Integer convenio) {
        final var bearerToken = getBearerToken();
        final var developerApplicationKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var requestedAt = InstantUtils.now();
        final var response = cobrancaBoletoFeignClient.createPixBoleto(
                bearerToken,
                developerApplicationKey,
                nossoNumero,
                new GeraPixBoletoRequest(convenio)
        );
        final var tempoResponse = InstantUtils.durationUntilNow(requestedAt);

        final var httpStatus = httpStatusFrom(response);
        final var url = urlFrom(response);
        final var requestBody = requestBodyFrom(response);
        final var responseBody = responseBodyFrom(response);
        final var pixBoletoResponse = readValue(httpStatus, responseBody, GeraPixBoletoResponse.class);
        final var requisicao = new Requisicao(url, requestBody, responseBody, tempoResponse);
        final var pixBoleto = pixBoletoResponse != null ? pixBoletoResponse.toDomain() : null;

        return new PixBoletoRequisicao(pixBoleto, requisicao);
    }

    private String getBearerToken() {
        final var token = oAuthClientService.generateToken();
        return token.getBearerToken();
    }

    private HttpStatus httpStatusFrom(final Response response) {
        return HttpStatus.resolve(response.status());
    }

    private String urlFrom(final Response response) {
        final var request = response.request();
        return request.url();
    }

    private String requestBodyFrom(final Response response) {
        final var requestBody = response.request().body();
        if (requestBody == null) {
            return null;
        }
        return new String(requestBody, StandardCharsets.UTF_8);
    }

    private String responseBodyFrom(final Response response) {
        final var httpStatus = httpStatusFrom(response);
        String responseBody = null;
        if (httpStatus.is2xxSuccessful()
                || httpStatus.is4xxClientError()
                || httpStatus.is5xxServerError()) {
            try (InputStream bodyIs = response.body().asInputStream()) {

                responseBody = StreamUtils.copyToString(bodyIs, StandardCharsets.UTF_8);

            } catch (IOException e) {
                responseBody = "Nao foi possivel converter response body para string. message %s".formatted(e.getMessage());
            }
        }
        return responseBody;
    }

    public <T> T readValue(final HttpStatus httpStatus, final String json, final Class<T> clazz) {
        if (!httpStatus.is2xxSuccessful()) return null;
        return Json.readValue(json, clazz);
    }
}