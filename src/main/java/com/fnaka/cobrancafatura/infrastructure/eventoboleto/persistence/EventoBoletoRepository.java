package com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoBoletoRepository extends JpaRepository<EventoBoletoJpaEntity, String> {

}
