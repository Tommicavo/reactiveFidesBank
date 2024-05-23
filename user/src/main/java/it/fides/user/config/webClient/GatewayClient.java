package it.fides.user.config.webClient;

import it.fides.user.models.dtos.TestDto;
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

    public Mono<TestDto> test(TestDto testDto) {
        return webClient.post()
                .uri("/banks/test")
                .bodyValue(testDto)
                .retrieve()
                .bodyToMono(TestDto.class);
    }
}
