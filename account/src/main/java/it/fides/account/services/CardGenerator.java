package it.fides.account.services;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class CardGenerator {

    public String getCardNumber() {
        return "4242424242424242";
    }

    public LocalDate getExpirationDate() {
        return LocalDate.of(2034, 12, 1);
    }

    public String getCvc() {
        return "567";
    }
}
