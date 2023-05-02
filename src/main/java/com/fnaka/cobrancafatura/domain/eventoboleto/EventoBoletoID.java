package com.fnaka.cobrancafatura.domain.eventoboleto;

import com.fnaka.cobrancafatura.domain.Identifier;
import com.fnaka.cobrancafatura.domain.utils.IdUtils;

import java.util.Objects;

public class EventoBoletoID extends Identifier {

    private final String value;

    public EventoBoletoID(String value) {
        this.value = value;
    }

    public static EventoBoletoID unique() {
        return EventoBoletoID.from(IdUtils.uuid());
    }

    public static EventoBoletoID from(String anId) {
        return new EventoBoletoID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoBoletoID that = (EventoBoletoID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
