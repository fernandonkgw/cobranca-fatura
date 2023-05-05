package com.fnaka.cobrancafatura.infrastructure.boleto.event;

import com.fnaka.cobrancafatura.domain.boleto.Boleto;
import org.springframework.context.ApplicationEvent;

public class BoletoRegistradoEvent extends ApplicationEvent {

    private final Boleto boleto;

    public BoletoRegistradoEvent(Object source, Boleto boleto) {
        super(source);
        this.boleto = boleto;
    }

    public Boleto getBoleto() {
        return boleto;
    }
}
