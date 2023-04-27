package com.fnaka.cobrancafatura.infrastructure.services.impl;

import com.fnaka.cobrancafatura.AmqpTest;
import com.fnaka.cobrancafatura.domain.boleto.BoletoCriadoEvent;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoCriadoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.json.Json;
import com.fnaka.cobrancafatura.infrastructure.services.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Disabled
@AmqpTest
class RabbitEventServiceTest {

    private static final String LISTENER = "boleto.criado";

    @Autowired
    @BoletoCriadoQueue
    private EventService publisher;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    void shouldSendMessage() throws InterruptedException {
        // given
        final var notification = new BoletoCriadoEvent("123");

        final var expectedMessage = Json.writeValueAsString(notification);

        // when
        this.publisher.send(notification);

        // then
        final var invocationData = harness.getNextInvocationDataFor(LISTENER, 1, TimeUnit.SECONDS);

        Assertions.assertNotNull(invocationData);
        Assertions.assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Component
    static class BoletoCriadoNewsListener {

        @RabbitListener(id = LISTENER, queues = "${amqp.queues.boleto-criado.routing-key}")
        void onBoletoCriado(@Payload String message) {
            System.out.println(message);
        }
    }
}
