package com.fnaka.cobrancafatura.domain.validation;

public enum ErrorCode {
    CFA_000("CFA-000", "Generic Error"),
    CFA_001("CFA-001", "'convenio' should not be null"),
    CFA_002("CFA-002", "'convenio' should not be less than zero"),
    CFA_003("CFA-003", "'numeroTituloCliente' should not be null"),
    CFA_004("CFA-004", "'numeroTituloCliente' should not be empty"),
    CFA_005("CFA-005", "'convenio' must be 7 characters");

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
