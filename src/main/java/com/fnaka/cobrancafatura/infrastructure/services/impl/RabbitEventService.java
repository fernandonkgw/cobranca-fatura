package com.fnaka.cobrancafatura.infrastructure.services.impl;

import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import com.fnaka.cobrancafatura.infrastructure.services.EventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;

public class RabbitEventService implements EventService {

    private final String exchange;
    private final String routingKey;
    private final RabbitOperations ops;

    public RabbitEventService(String exchange, String routingKey, RabbitOperations ops) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.ops = ops;
    }

    @Override
    public void send(Object event) {
        this.ops.convertAndSend(this.exchange, this.routingKey, Json.writeValueAsString(event));
    }
}
