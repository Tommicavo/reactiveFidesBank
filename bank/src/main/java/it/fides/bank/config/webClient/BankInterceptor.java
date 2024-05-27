package it.fides.bank.config.webClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Component
public class BankInterceptor implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String requestUrl = request.getURI().toString();
        if (!requestUrl.startsWith("http://bank-service:9094")) {
            return sendErrorResponse(response, HttpStatus.FORBIDDEN, "Unauthorize request. Call your endpoint at 'http://localhost:9090/' url");
        }

        String fromWithinHeader = request.getHeaders().getFirst("FromWithin");
        if ("true".equalsIgnoreCase(fromWithinHeader)) {
            return chain.filter(exchange);
        }

        String unsecuredRouteHeader = request.getHeaders().getFirst("UnsecuredRoute");
        if ("true".equalsIgnoreCase(unsecuredRouteHeader)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return sendErrorResponse(response, HttpStatus.FORBIDDEN, "Missing authorization token");
        }

        return chain.filter(exchange).contextWrite(Context.of("Authorization", authHeader));
    }

    private Mono<Void> sendErrorResponse(ServerHttpResponse response, HttpStatus status, String message) {
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json");
        String body = "{\"error\": \"" + message + "\"}";
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
