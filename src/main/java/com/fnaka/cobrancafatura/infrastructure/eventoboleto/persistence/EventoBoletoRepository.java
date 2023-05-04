package com.fnaka.cobrancafatura.infrastructure.eventoboleto.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoBoletoRepository extends JpaRepository<EventoBoletoJpaEntity, String> {

    List<EventoBoletoJpaEntity> findByBoletoId(String boletoId);
}
