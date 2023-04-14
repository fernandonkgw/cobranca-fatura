package com.fnaka.cobrancafatura.domain.validation;

public enum ErrorCode {
    CFA_000("CFA-000", "Generic Error"),
    CFA_001("CFA-001", "'convenio' should not be null"),
    CFA_002("CFA-002", "'convenio' should not be less than zero");

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
