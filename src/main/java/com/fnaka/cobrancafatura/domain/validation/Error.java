package com.fnaka.cobrancafatura.domain.validation;

import java.util.Collections;
import java.util.List;

public record Error(
        String message,
        String code,
        List<String> params
) {

    public static Error with(final String message, final String code) {
        return new Error(message, code, Collections.emptyList());
    }

    public static Error with(final String message) {
        return Error.with(message, "CFA-000");
    }

    public static Error with(final String message, final String code, Object param) {
        final var aParam = param.toString();
        return new Error(message, code, List.of(aParam));
    }

    public String getFirstParam() {
        if (this.params.isEmpty()) {
            return null;
        }
        return this.params.get(0);
    }
}
