package it.fides.gateway.services;

import it.fides.gateway.models.repositories.BlackTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BlackTokenService {

    @Autowired
    private BlackTokenRepository blackTokenRepo;

    public Mono<Boolean> isTokenBlackListed(String token) {
        return blackTokenRepo.findByValueBlackToken(token)
                .map(blackTokenEntity -> true)
                .defaultIfEmpty(false);
    }
}
