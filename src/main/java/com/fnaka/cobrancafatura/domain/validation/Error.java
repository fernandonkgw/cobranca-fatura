package com.fnaka.cobrancafatura.domain.validation;

public record Error(String message, String code) {

    public static Error with(final String message, final String code) {
        return new Error(message, code);
    }

    public static Error with(final String message) {
        return new Error(message, "CFA-000");
    }
}
