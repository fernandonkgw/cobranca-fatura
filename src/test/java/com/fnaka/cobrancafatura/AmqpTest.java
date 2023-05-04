package com.fnaka.cobrancafatura;

import com.fnaka.cobrancafatura.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("local")
@SpringBootTest(classes = WebServerConfig.class)
public @interface AmqpTest {
}
