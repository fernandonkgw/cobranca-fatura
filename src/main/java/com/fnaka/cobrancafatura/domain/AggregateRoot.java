package com.fnaka.cobrancafatura.domain;

import com.fnaka.cobrancafatura.domain.events.DomainEvent;

import java.util.List;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AggregateRoot(final ID id) {
        super(id);
    }

    protected AggregateRoot(final ID id, List<DomainEvent> domainEvents) {
        super(id, domainEvents);
    }
}
