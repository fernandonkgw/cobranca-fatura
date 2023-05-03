package com.fnaka.cobrancafatura.infrastructure.services.impl.bancobrasil;

import com.fnaka.cobrancafatura.domain.boleto.CobrancaBoleto;
import com.fnaka.cobrancafatura.domain.boleto.PixBoleto;
import com.fnaka.cobrancafatura.domain.dtos.CobrancaBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.dtos.PixBoletoRequisicao;
import com.fnaka.cobrancafatura.domain.eventoboleto.Requisicao;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.BancoBrasilCredential;
import com.fnaka.cobrancafatura.infrastructure.services.CobrancaBoletoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        final var token = oAuthClientService.generateToken();
        final var bearerToken = token.getBearerToken();
        final var developerApplicationKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var response = cobrancaBoletoFeignClient.findByNossoNumeroAndConvenio(
                bearerToken,
                developerApplicationKey,
                nossoNumero,
                convenio
        );

        final var httpStatus = HttpStatus.resolve(response.status());
        final var request = response.request();
        final var url = request.url();
        String requestBody = null;
        String responseBody = null;
        CobrancaResponse cobrancaResponse = null;
        if (request.body() != null) {
            requestBody = new String(request.body(), StandardCharsets.UTF_8);
        }

        if (httpStatus.is2xxSuccessful()
                || httpStatus.is4xxClientError()
                || httpStatus.is5xxServerError()) {
            try (InputStream bodyIs = response.body().asInputStream()) {

                responseBody = StreamUtils.copyToString(bodyIs, StandardCharsets.UTF_8);
                if (httpStatus.is2xxSuccessful()) {
                    cobrancaResponse = Json.readValue(responseBody, CobrancaResponse.class);
                }

            } catch (IOException e) {
                responseBody = "Nao foi possivel converter response body para string. message %s".formatted(e.getMessage());
            }
        }
        final var requisicao = new Requisicao(url, requestBody, responseBody);
        final var cobrancaBoleto = cobrancaResponse != null ? cobrancaResponse.toDomain() :null;


        return new CobrancaBoletoRequisicao(cobrancaBoleto, requisicao);
    }

    @Override
    public PixBoletoRequisicao createPix(String nossoNumero, Integer convenio) {
        final var token = oAuthClientService.generateToken();
        final var bearerToken = token.getBearerToken();
        final var developerApplicationKey = bancoBrasilCredential.getDeveloperApplicationKey();
        final var response = cobrancaBoletoFeignClient.createPixBoleto(
                bearerToken,
                developerApplicationKey,
                nossoNumero,
                new GeraPixBoletoRequest(convenio)
        );

        final var httpStatus = HttpStatus.resolve(response.status());
        final var request = response.request();
        final var url = request.url();
        String requestBody = null;
        String responseBody = null;
        GeraPixBoletoResponse pixBoletoResponse = null;
        if (request.body() != null) {
            requestBody = new String(request.body(), StandardCharsets.UTF_8);
        }
        if (httpStatus.is2xxSuccessful()
                || httpStatus.is4xxClientError()
                || httpStatus.is5xxServerError()) {
            try (InputStream bodyIs = response.body().asInputStream()) {

                responseBody = StreamUtils.copyToString(bodyIs, StandardCharsets.UTF_8);
                if (httpStatus.is2xxSuccessful()) {
                    pixBoletoResponse = Json.readValue(responseBody, GeraPixBoletoResponse.class);
                }

            } catch (IOException e) {
                responseBody = "Nao foi possivel converter response body para string. message %s".formatted(e.getMessage());
            }
        }
        final var requisicao = new Requisicao(url, requestBody, responseBody);
        final var pixBoleto = pixBoletoResponse != null ? pixBoletoResponse.toDomain() : null;

        return new PixBoletoRequisicao(pixBoleto, requisicao);
    }
}