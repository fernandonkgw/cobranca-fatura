package com.fnaka.cobrancafatura;

import com.fnaka.cobrancafatura.infrastructure.boleto.persistence.BoletoRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

public class PostgreSQLCleanUpExtension implements BeforeEachCallback {


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        final var appContext = SpringExtension.getApplicationContext(context);

        cleanUp(List.of(
                appContext.getBean(BoletoRepository.class)
        ));
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
