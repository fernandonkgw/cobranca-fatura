package com.fnaka.cobrancafatura.domain.validation;

public enum ErrorCode {
    CFA_000("CFA-000", "Generic Error"),
    CFA_001("CFA-001", "'convenio' should not be null"),
    CFA_002("CFA-002", "'convenio' should not be less than zero"),
    CFA_003("CFA-003", "'nossoNumero' should not be null"),
    CFA_004("CFA-004", "'nossoNumero' should not be empty"),
    CFA_005("CFA-005", "'convenio' must be 7 characters"),
    CFA_006("CFA-006", "'boleto' was not found"),
    CFA_007("CFA-007", "'status' must be REGISTRADO to generate Pix"),
    CFA_008("CFA-008", "'nossoNumero' must be 20 characters"),
    CFA_009("CFA-009", "Error generating pix"),
    CFA_010("CFA-010", "'status' must be PIX_CRIADO");

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
