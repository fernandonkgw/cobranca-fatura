package com.fnaka.cobrancafatura.domain.exceptions;

import com.fnaka.cobrancafatura.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    private DomainException(final List<Error> errors) {
        super("");
        this.errors = errors;
    }

    public static DomainException with(final Error anError) {
        return new DomainException(List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException(anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Error getFirstError() {
        return errors.get(0);
    }
}
