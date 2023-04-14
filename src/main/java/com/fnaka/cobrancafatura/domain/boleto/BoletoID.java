package com.fnaka.cobrancafatura.domain.boleto;

import com.fnaka.cobrancafatura.domain.Identifier;
import com.fnaka.cobrancafatura.domain.utils.IdUtils;

import java.util.Objects;

public class BoletoID extends Identifier {

    private final String value;

    private BoletoID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static BoletoID from(final String anId) {
        return new BoletoID(anId);
    }

    public static BoletoID unique() {
        return BoletoID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoletoID boletoID = (BoletoID) o;
        return Objects.equals(value, boletoID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
