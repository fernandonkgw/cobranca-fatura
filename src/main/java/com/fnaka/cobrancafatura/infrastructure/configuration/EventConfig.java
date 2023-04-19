package com.fnaka.cobrancafatura.infrastructure.configuration;

import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoCriadoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoRegistradoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.QueueProperties;
import com.fnaka.cobrancafatura.infrastructure.services.EventService;
import com.fnaka.cobrancafatura.infrastructure.services.impl.RabbitEventService;
import com.fnaka.cobrancafatura.infrastructure.services.local.InMemoryEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventConfig {

    @Bean
    @BoletoCriadoQueue
    @Profile({"test"})
    EventService localBoletoCriadoEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @BoletoRegistradoQueue
    @Profile({"test"})
    EventService localBoletoRegistradoEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @BoletoCriadoQueue
//    @ConditionalOnMissingBean
    @Profile({"local"})
    EventService boletoCriadoEventService(
            @BoletoCriadoQueue QueueProperties props,
            RabbitOperations ops
            ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }

    @Bean
    @BoletoRegistradoQueue
//    @ConditionalOnMissingBean
    @Profile({"local"})
    EventService boletoRegistradoEventService(
            @BoletoRegistradoQueue QueueProperties props,
            RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
