package com.fnaka.cobrancafatura.infrastructure.boleto;

import com.fnaka.cobrancafatura.domain.boleto.CobrancaGateway;
import org.springframework.stereotype.Component;

@Component
public class DefaultCobrancaGateway implements CobrancaGateway {
    @Override
    public Boolean confirmaRegistro(Integer convenio, String numeroTituloCliente) {
        return true;
    }
}
