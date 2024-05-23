package it.fides.bank.models.repositories;

import it.fides.bank.models.entities.BankEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends R2dbcRepository<BankEntity, Long> {
}
