package it.fides.user.models.repositories;

import it.fides.user.models.entities.UserAccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends R2dbcRepository<UserAccountEntity, Long> {
}
