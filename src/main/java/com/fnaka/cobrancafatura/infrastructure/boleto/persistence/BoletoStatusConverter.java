package com.fnaka.cobrancafatura.infrastructure.boleto.persistence;

import com.fnaka.cobrancafatura.domain.boleto.BoletoStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BoletoStatusConverter implements AttributeConverter<BoletoStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BoletoStatus status) {
        if (status == null) return null;
        return status.getCodigo();
    }

    @Override
    public BoletoStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        return BoletoStatus.of(dbData).orElse(null);
    }
}
