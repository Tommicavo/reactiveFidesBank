package it.fides.gateway.config.webClient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;

@Component
public class UnsecureRoutesExchangeFilter {
    // Propagate FromWithin header for skip validation also in target microservice
    public ExchangeFilterFunction filter() {
        return (ClientRequest request, ExchangeFunction next) -> {
            ClientRequest updatedRequest = ClientRequest.from(request)
                    .header("FromWithin", "true")
                    .build();
            return next.exchange(updatedRequest);
        };
    }
}
