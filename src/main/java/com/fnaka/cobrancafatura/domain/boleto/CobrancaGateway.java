package com.fnaka.cobrancafatura.domain.boleto;

public interface CobrancaGateway {

    Boolean confirmaRegistro(Integer convenio, String numeroTituloCliente);
}
