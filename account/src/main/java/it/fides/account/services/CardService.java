package it.fides.account.services;

import it.fides.account.models.entities.CardEntity;
import it.fides.account.models.repositories.CardRepository;
import it.fides.account.utils.AccountLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private CardGenerator cardGenerator;

    @Autowired
    private AccountLogger accountLogger;

    public Mono<CardEntity> getCard(Long id) {
        return cardRepo.findById(id);
    }

    public Mono<String> insertCard(Long id) {
        CardEntity card = new CardEntity();
        card.setCardNumber(cardGenerator.getCardNumber());
        card.setExpirationDate(cardGenerator.getExpirationDate());
        card.setCvc(cardGenerator.getCvc());
        card.setIdAccount(id);
        return cardRepo.save(card)
                .doOnError(err -> Mono.error(new RuntimeException("Error generating the card: " + err.getMessage())))
                .then(Mono.just("New Card Generated"));
    }
}
