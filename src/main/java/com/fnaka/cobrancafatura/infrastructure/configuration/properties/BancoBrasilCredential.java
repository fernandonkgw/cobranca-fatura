package com.fnaka.cobrancafatura.infrastructure.configuration.properties;

public class BancoBrasilCredential {

    private String clientId;
    private String clientSecret;

    private String developerApplicationKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDeveloperApplicationKey() {
        return developerApplicationKey;
    }

    public void setDeveloperApplicationKey(String developerApplicationKey) {
        this.developerApplicationKey = developerApplicationKey;
    }
}
