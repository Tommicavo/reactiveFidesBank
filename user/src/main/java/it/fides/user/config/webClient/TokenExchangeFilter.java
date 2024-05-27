package it.fides.user.config.webClient;

import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class TokenExchangeFilter implements ExchangeFilterFunction {

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return Mono.deferContextual(ctx -> {
            if (!ctx.hasKey("Authorization")) {
                return Mono.error(new IllegalStateException("Missing Authorization Header"));
            }
            String authHeader = ctx.get("Authorization");
            ClientRequest updatedRequest = ClientRequest.from(request)
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .build();
            return next.exchange(updatedRequest);
        });
    }
}
