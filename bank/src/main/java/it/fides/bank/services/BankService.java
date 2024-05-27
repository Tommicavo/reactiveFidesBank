package it.fides.bank.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.fides.bank.models.dtos.JwtData;
import it.fides.bank.models.entities.BankEntity;
import it.fides.bank.models.repositories.BankRepository;
import it.fides.bank.util.BankLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepo;

    @Autowired
    private BankLogger bankLogger;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public Mono<String> healthcheck() {
        return Mono.just("Bank Microservice ok");
    }

    public JwtData extractJwtData(ServerWebExchange exchange) {
        String token = Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst("Authorization")).substring(7);
        Long id = Long.parseLong(Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject());
        String role = (String) Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build().parseClaimsJws(token).getBody().get("role");
        return new JwtData(id, role);
    }

    public Flux<BankEntity> getAllBanks() {
        return bankRepo.findAll().switchIfEmpty(Mono.error(new RuntimeException("Banks not found")));
    }

    public Mono<BankEntity> getBank(Long id) {
        return bankRepo.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Bank not found")));
    }

    public Mono<String> insertBank(BankEntity bank) {
        return bankRepo.save(bank)
                .doOnError(err -> bankLogger.log.error("Error in insertBank(): " + err.getMessage()))
                .then(Mono.just("New Bank registered"));
    }
}
