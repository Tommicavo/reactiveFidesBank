package it.fides.gateway.config.auth;

import java.util.Arrays;
import java.util.List;

public class UnsecuredRoutes {

    private static final List<String> UNSECURED_ROUTES = Arrays.asList(
            "/auth/signin",
            "/auth/login",
            "/users/healthcheck",
            "/banks/healthcheck",
            "/transactions/healthcheck",
            "/accounts/healthcheck"
    );

    public static List<String> getUnsecuredRoutes() {
        return UNSECURED_ROUTES;
    }
}
