package it.fides.user.models.repositories;

import it.fides.user.models.entities.RoleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends R2dbcRepository<RoleEntity, Long> {
}
