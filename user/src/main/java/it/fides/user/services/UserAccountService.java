package it.fides.user.services;

import it.fides.user.models.dtos.EnrollmentDto;
import it.fides.user.models.entities.UserAccountEntity;
import it.fides.user.models.repositories.UserAccountRepository;
import it.fides.user.util.UserLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private UserLogger userLogger;

    public Mono<String> assignOwnersToAccount(EnrollmentDto enrollmentDto) {
        userLogger.log.info("assignOwnersToAccount method called");

        // Create a list of UserAccountEntity to save
        List<UserAccountEntity> userAccountEntities = new ArrayList<>();
        for (Long idOwner : enrollmentDto.getIdOwners()) {
            UserAccountEntity userAccountEntity = new UserAccountEntity();
            userAccountEntity.setIdAccount(enrollmentDto.getIdAccount());
            userAccountEntity.setIdUser(idOwner);
            userAccountEntities.add(userAccountEntity);
        }

        // Save all entities at once
        return userAccountRepo.saveAll(userAccountEntities)
                .collectList() // Collect the saved entities into a list
                .doOnSuccess(savedEntities -> userLogger.log.info("Saved UserAccountEntities for accountId: " + enrollmentDto.getIdAccount()))
                .then(Mono.just("Owners assigned to Bank Account"))
                .onErrorResume(err -> {
                    userLogger.log.error("Error assigning owners to bank account: ", err);
                    return Mono.error(new RuntimeException("Error assigning owners to bank account: " + err));
                });
    }
}
