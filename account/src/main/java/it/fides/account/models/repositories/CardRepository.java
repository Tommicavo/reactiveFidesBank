package it.fides.account.models.repositories;

import it.fides.account.models.entities.CardEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends R2dbcRepository<CardEntity, Long> {
}
