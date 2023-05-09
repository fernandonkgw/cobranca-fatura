package com.fnaka.cobrancafatura.application.eventoboleto.notifica;

import com.fnaka.cobrancafatura.domain.eventoboleto.EventoBoletoGateway;

public class DefaultNotificaEventoBoletoUseCase extends NotificaEventoBoletoUseCase {

    private final EventoBoletoGateway eventoBoletoGateway;

    public DefaultNotificaEventoBoletoUseCase(EventoBoletoGateway eventoBoletoGateway) {
        this.eventoBoletoGateway = eventoBoletoGateway;
    }

    @Override
    public NotificaEventoBoletoOutput execute(String anId) {

        return null;
    }
}
