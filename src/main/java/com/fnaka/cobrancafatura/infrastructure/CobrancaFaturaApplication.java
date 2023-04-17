package com.fnaka.cobrancafatura.infrastructure;

import com.fnaka.cobrancafatura.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class CobrancaFaturaApplication {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "local");
        SpringApplication.run(WebServerConfig.class, args);
    }

}
