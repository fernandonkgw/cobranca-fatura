package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.validation.Error;
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
        if (this.boleto.getConvenio() == null) {
            this.validationHandler().append(Error.with("'convenio' should not be null", "CFA-001"));
        }
    }
}
