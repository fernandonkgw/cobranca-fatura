package com.fnaka.cobrancafatura.domain.validation;

import java.util.Collections;
import java.util.List;

public record Error(
        String message,
        ErrorCode code,
        List<String> params
) {

    public static Error with(final ErrorCode code) {
        return new Error(code.getMessage(), code, Collections.emptyList());
    }

    public static Error with(final String message) {
        return new Error(message, ErrorCode.CFA_000, Collections.emptyList());
    }

    public static Error with(final ErrorCode code, Object param) {
        final var aParam = param.toString();
        return new Error(code.getMessage(), code, List.of(aParam));
    }

    public String getFirstParam() {
        if (this.params.isEmpty()) {
            return null;
        }
        return this.params.get(0);
    }
}
