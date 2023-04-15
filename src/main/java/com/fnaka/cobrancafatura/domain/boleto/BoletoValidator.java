package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;
import com.fnaka.cobrancafatura.domain.validation.Validator;

public class BoletoValidator extends Validator {

    private static final int NUMERO_CONVENIO_VALID_LENGTH = 7;
    private final Boleto boleto;

    protected BoletoValidator(final Boleto boleto, final ValidationHandler aHandler) {
        super(aHandler);
        this.boleto = boleto;
    }

    @Override
    public void validate() {
        checkConvenioConstraints();
        checkNumeroTituloClienteConstraints();
    }

    private void checkConvenioConstraints() {
        final var convenio = this.boleto.getConvenio();

        if (convenio == null) {
            this.validationHandler().append(Error.with(ErrorCode.CFA_001));
            return;
        }

        if (convenio < 0) {
            this.validationHandler().append(Error.with(ErrorCode.CFA_002, convenio));
            return;
        }

        final var length = String.valueOf(convenio).length();
        if (length != NUMERO_CONVENIO_VALID_LENGTH) {
            this.validationHandler().append(Error.with(ErrorCode.CFA_005, convenio));
        }
    }

    private void checkNumeroTituloClienteConstraints() {
        final var numeroTituloCliente = this.boleto.getNumeroTituloCliente();
        if (numeroTituloCliente == null) {
            this.validationHandler().append(Error.with(ErrorCode.CFA_003));
            return;
        }

        if (numeroTituloCliente.isBlank()) {
            this.validationHandler().append(Error.with(ErrorCode.CFA_004));
        }
    }
}
