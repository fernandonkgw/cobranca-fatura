CREATE TABLE eventos_boleto (
    id VARCHAR(36) NOT NULL PRIMARY KEY ,
    boleto_id VARCHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    tempo_execucao NUMERIC(8),
    url VARCHAR(1000),
    payload_request VARCHAR(4000),
    payload_response VARCHAR(4000)
);