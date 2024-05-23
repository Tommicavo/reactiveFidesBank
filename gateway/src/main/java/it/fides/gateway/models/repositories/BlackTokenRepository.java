package it.fides.gateway.models.repositories;

import it.fides.gateway.models.entities.BlackTokenEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BlackTokenRepository extends R2dbcRepository<BlackTokenEntity, Long> {
    Mono<BlackTokenEntity> findByValueBlackToken(String valueBlackToken);
}
