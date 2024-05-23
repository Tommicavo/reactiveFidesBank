package it.fides.gateway.config.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.fides.gateway.services.JwtService;
import it.fides.gateway.util.GatewayLogger;
import jakarta.annotation.Priority;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Priority(1)
public class JwtFilter implements WebFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objMapper;

    @Autowired
    private GatewayLogger gatewayLogger;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // Verify if the request originated from within gateway microservice, not through it
        // It handles requests made before token generation, like getUserByEmail during the login process
        if (isFromWithinGateway(exchange)) {
            gatewayLogger.log.info("Call from within Gateway");
            return chain.filter(exchange);
        }

        // Verify if the request is an unsecured route
        // If so, just skip token validation both here and in the target microservice
        if (isUnsecuredRoute(exchange)) {
            gatewayLogger.log.info("Unsecured Route called");
            ServerWebExchange unsecuredExchange = jwtService.injectUnsecuredHeader(exchange);
            return chain.filter(unsecuredExchange);
        }

        // Otherwise proceed with access token verification
        String token = jwtService.getJwtFromRequest(exchange);
        if (token != null) {
            return jwtService.verifyToken(token)
                    .flatMap(accessToken -> {
                        if (accessToken.isVerified()) {
                            gatewayLogger.log.info("AccessToken: " + accessToken.toString());
                            // Propagate token for further validation in target microservice
                            ServerWebExchange tokenExchange = jwtService.injectAuthorizationToken(exchange, token);
                            return chain.filter(tokenExchange);
                        } else {
                            return handleTokenError(exchange, accessToken.getErrorMessage());
                        }
                    });
        } else {
            return handleTokenError(exchange, "MISSING TOKEN");
        }
    }

    private Mono<Void> handleTokenError(ServerWebExchange exchange, String tokenError) {
        gatewayLogger.log.info("filter error: " + tokenError);
        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            return exchange.getResponse().writeWith(Mono.just(objMapper.writeValueAsString("JwtFilter Token Error: " + tokenError))
                    .map(s -> exchange.getResponse().bufferFactory().wrap(s.getBytes())));
        } catch (JsonProcessingException e) {
            return Mono.error(new RuntimeException("Exception Error in JwtFilter: " + e.getMessage()));
        }
    }

    private boolean isFromWithinGateway(ServerWebExchange exchange) {
        String fromWithinHeader = exchange.getRequest().getHeaders().getFirst("FromWithin");
        return "true".equalsIgnoreCase(fromWithinHeader);
    }

    private boolean isUnsecuredRoute(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().toString();
        List<String> unsecuredRoutes = UnsecuredRoutes.getUnsecuredRoutes();
        return unsecuredRoutes.stream().anyMatch(route -> new AntPathMatcher().match(route, path));
    }
}
