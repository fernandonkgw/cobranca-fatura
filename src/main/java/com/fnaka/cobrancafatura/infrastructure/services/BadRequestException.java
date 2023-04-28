package com.fnaka.cobrancafatura.infrastructure.services;

import com.fnaka.cobrancafatura.domain.exceptions.NoStacktraceException;

public class BadRequestException extends NoStacktraceException {

    private final String responseBody;
    private final String url;
    private final String requestBody;

    public BadRequestException(String message, String responseBody, String url, String requestBody) {
        super(message);
        this.responseBody = responseBody;
        this.url = url;
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestBody() {
        return requestBody;
    }
}
