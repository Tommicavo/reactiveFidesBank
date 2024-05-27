package it.fides.account.config.webClient;

import it.fides.account.models.dtos.EnrollmentDto;
import it.fides.account.models.dtos.JwtData;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Component
public class GatewayClient {

    private final WebClient webClient;
    public GatewayClient(WebClient.Builder webClientBuilder, TokenExchangeFilter tokenExchangeFilter, Environment env) {
        this.webClient = webClientBuilder.baseUrl(Objects.requireNonNull(env.getProperty("gateway.url")))
                .filter(tokenExchangeFilter)
                .build();
    }

    public Mono<JwtData> extractJwtData() {
        return webClient.get()
                .uri("/auth/extractJwtData")
                .retrieve()
                .bodyToMono(JwtData.class);
    }

    public Mono<String> assignOwnersToAccount(EnrollmentDto enrollmentDto) {
        return webClient.post()
                .uri("/users/accounts/enrollment")
                .bodyValue(enrollmentDto)
                .retrieve()
                .bodyToMono(String.class);
    }
}
