package com.fnaka.cobrancafatura.infrastructure.utils;

import java.util.Base64;

public final class AuthorizationUtils {

    private AuthorizationUtils() {}

    public static String basicAuth(final String username, final String password) {
        return "Basic " + Base64.getEncoder().encodeToString(username.concat(":").concat(password).getBytes());
    }
}
