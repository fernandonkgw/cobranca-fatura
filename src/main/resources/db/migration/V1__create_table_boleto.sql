CREATE TABLE boletos (
    id VARCHAR(36) NOT NULL PRIMARY KEY ,
    convenio INTEGER NOT NULL,
    numero_titulo_cliente VARCHAR(20) NOT NULL,
    status VARCHAR(10) NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP NOT NULL
);