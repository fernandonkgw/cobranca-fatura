package com.fnaka.cobrancafatura.infrastructure.boleto.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletoRepository extends JpaRepository<BoletoJpaEntity, String> {
}
