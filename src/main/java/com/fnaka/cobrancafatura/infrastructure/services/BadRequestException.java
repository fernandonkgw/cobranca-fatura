package com.fnaka.cobrancafatura.infrastructure.services;

import com.fnaka.cobrancafatura.domain.exceptions.NoStacktraceException;

public class BadRequestException extends NoStacktraceException {
    public BadRequestException(String message) {
        super(message);
    }
}
