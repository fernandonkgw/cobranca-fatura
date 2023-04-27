package com.fnaka.cobrancafatura;

import com.fnaka.cobrancafatura.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@SpringBootTest(classes = WebServerConfig.class)
@Disabled
public @interface ServiceTest {
}
