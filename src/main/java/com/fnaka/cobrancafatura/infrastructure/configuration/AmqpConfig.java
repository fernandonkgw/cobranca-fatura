package com.fnaka.cobrancafatura.infrastructure.configuration;

import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoCriadoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoEvents;
import com.fnaka.cobrancafatura.infrastructure.configuration.annotations.BoletoRegistradoQueue;
import com.fnaka.cobrancafatura.infrastructure.configuration.properties.QueueProperties;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.boleto-criado")
    @BoletoCriadoQueue
    QueueProperties boletoCriadoQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.boleto-registrado")
    @BoletoRegistradoQueue
    QueueProperties boletoRegistradoQueueProperties() {
        return new QueueProperties();
    }

    @Configuration
    static class Admin {

        @Bean
        @BoletoEvents
        DirectExchange boletoEventsExchange(@BoletoCriadoQueue QueueProperties props) {
            return new DirectExchange(props.getExchange());
        }

        @Bean
        @BoletoCriadoQueue
        Queue boletoCriadoQueue(@BoletoCriadoQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @BoletoCriadoQueue
        Binding boletoCriadoQueueBinding(
                @BoletoEvents DirectExchange exchange,
                @BoletoCriadoQueue Queue queue,
                @BoletoCriadoQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }

        @Bean
        @BoletoRegistradoQueue
        Queue boletoRegistradoQueue(@BoletoRegistradoQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @BoletoRegistradoQueue
        Binding boletoRegistradoQueueBinding(
                @BoletoEvents DirectExchange exchange,
                @BoletoRegistradoQueue Queue queue,
                @BoletoRegistradoQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }
    }
}
