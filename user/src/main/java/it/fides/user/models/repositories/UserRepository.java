package it.fides.user.models.repositories;

import it.fides.user.models.entities.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByEmailUser(String email);
}
