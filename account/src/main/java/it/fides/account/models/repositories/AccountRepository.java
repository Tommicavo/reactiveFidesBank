package it.fides.account.models.repositories;

import it.fides.account.models.entities.AccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends R2dbcRepository<AccountEntity, Long> {
}
