package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.validation.Error;
import com.fnaka.cobrancafatura.domain.validation.ErrorCode;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;
import com.fnaka.cobrancafatura.domain.validation.Validator;

public class BoletoValidator extends Validator {

    private final Boleto boleto;

    protected BoletoValidator(final Boleto boleto, final ValidationHandler aHandler) {
        super(aHandler);
        this.boleto = boleto;
    }

    @Override
    public void validate() {
        checkConvenioConstraints();
    }

    private void checkConvenioConstraints() {
        final var convenio = this.boleto.getConvenio();
        if (convenio == null) {
            this.validationHandler().append(Error.with(ErrorCode.CFA_001));
            return;
        }

        if (convenio < 0) {
            this.validationHandler().append(Error.with(ErrorCode.CFA_002, convenio));
        }
    }
}
