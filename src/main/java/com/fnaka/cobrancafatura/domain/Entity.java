package com.fnaka.cobrancafatura.domain;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;
import com.fnaka.cobrancafatura.domain.events.DomainEventPublisher;
import com.fnaka.cobrancafatura.domain.validation.ValidationHandler;

import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;
    private DomainEvent domainEvent;

    protected Entity(final ID id) {
        this(id, null);
    }

    protected Entity(final ID id, final DomainEvent domainEvent) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
        this.domainEvent = domainEvent;
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

    public DomainEvent getDomainEvent() {
        return this.domainEvent;
    }

    public void publishDomainEvent(final DomainEventPublisher publisher) {
        if (publisher == null || this.domainEvent == null) {
            return;
        }

        publisher.publishEvent(this.domainEvent);
        this.domainEvent = null;
    }

    public void registerEvent(final DomainEvent event) {
        if (event == null) {
            return;
        }

        this.domainEvent = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
