package com.fnaka.cobrancafatura.domain.events;

@FunctionalInterface
public interface DomainEventPublisher {

    void publishEvent(DomainEvent event);
}
