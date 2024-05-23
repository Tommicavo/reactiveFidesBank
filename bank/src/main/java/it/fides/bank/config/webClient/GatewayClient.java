package it.fides.bank.config.webClient;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
public class GatewayClient {

    private final WebClient webClient;
    public GatewayClient(WebClient.Builder webClientBuilder, TokenExchangeFilter tokenExchangeFilter, Environment env) {
        this.webClient = webClientBuilder.baseUrl(Objects.requireNonNull(env.getProperty("gateway.url")))
                .filter(tokenExchangeFilter)
                .build();
    }
}
