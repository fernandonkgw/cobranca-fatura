package com.fnaka.cobrancafatura.infrastructure.configuration;

import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoCriadoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.QueueProperties;
import com.fnaka.cobrancafatura.infrastructure.services.EventService;
import com.fnaka.cobrancafatura.infrastructure.services.impl.RabbitEventService;
import com.fnaka.cobrancafatura.infrastructure.services.local.InMemoryEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventConfig {

    @Bean
    @BoletoCriadoQueue
    @Profile({"development"})
    EventService localBoletoCriadoEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @BoletoCriadoQueue
    @ConditionalOnMissingBean
    EventService boletoCriadoEventService(
            @BoletoCriadoQueue QueueProperties props,
            RabbitOperations ops
            ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
