CREATE TABLE boletos (
    id VARCHAR(36) NOT NULL PRIMARY KEY ,
    convenio INTEGER NOT NULL,
    nosso_numero VARCHAR(20) NOT NULL,
    status NUMERIC(3) NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL,
    url VARCHAR(500),
    txid VARCHAR(40),
    emv VARCHAR(500)
);