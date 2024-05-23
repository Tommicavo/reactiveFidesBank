package it.fides.gateway.config.webClient;

import it.fides.gateway.models.dtos.UserDto;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Component
public class GatewayClient {

    private final WebClient webClient;
    public GatewayClient(WebClient.Builder webClientBuilder, UnsecureRoutesExchangeFilter unsecuredRouteExchangeFilter, Environment env) {
        this.webClient = webClientBuilder.baseUrl(Objects.requireNonNull(env.getProperty("gateway.url")))
                .filter(unsecuredRouteExchangeFilter.filter())
                .build();
    }

    public Mono<UserDto> getUser(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<String> signin(UserDto userDto) {
        return webClient.post()
                .uri("/users/signin")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<UserDto> getUserByEmail(UserDto userDto) {
        return webClient.post()
                .uri("/users/user-by-email")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}
