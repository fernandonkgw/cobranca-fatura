package com.fnaka.cobrancafatura;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@ComponentScan(
        basePackages = "com.fnaka.cobrancafatura",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*PostgreSQLGateway")
        }
)
@DataJpaTest
@ExtendWith(PostgreSQLCleanUpExtension.class)
public @interface PostgreSQLGatewayTest {
}
