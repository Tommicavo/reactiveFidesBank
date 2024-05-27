package it.fides.account.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.fides.account.config.webClient.GatewayClient;
import it.fides.account.models.dtos.AccountDto;
import it.fides.account.models.dtos.EnrollmentDto;
import it.fides.account.models.dtos.JwtData;
import it.fides.account.models.entities.AccountEntity;
import it.fides.account.models.repositories.AccountRepository;
import it.fides.account.utils.AccountLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CardService cardService;

    @Autowired
    private GatewayClient gatewayClient;

    @Autowired
    private AccountLogger accountLogger;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public JwtData extractJwtData(ServerWebExchange exchange) {
        String token = Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst("Authorization")).substring(7);
        Long id = Long.parseLong(Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject());
        String role = (String) Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build().parseClaimsJws(token).getBody().get("role");
        return new JwtData(id, role);
    }

    public Mono<String> healthcheck() {
        return Mono.just("account microservice ok");
    }

    public Flux<AccountEntity> getAllAccounts() {
        return accountRepo.findAll().switchIfEmpty(Mono.error(new RuntimeException("Accounts not found")));
    }

    public Mono<AccountEntity> getAccount(Long id) {
        return accountRepo.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Account not found")));
    }

    public Mono<String> insertAccount(AccountDto accountDto, Long id) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAmountAccount(0L);
        accountEntity.setNameAccount(accountDto.getNameAccount());
        accountEntity.setIdBank(accountDto.getIdBank());
        return accountRepo.save(accountEntity)
                .flatMap(account -> cardService.insertCard(account.getIdAccount())
                        .flatMap(cardResponse -> {
                            List<Long> owners = new ArrayList<>();
                            owners.add(id);
                            if (!accountDto.getCoOwnersList().isEmpty()) {
                                owners.addAll(accountDto.getCoOwnersList());
                            }
                            return gatewayClient.assignOwnersToAccount(new EnrollmentDto(account.getIdAccount(), owners))
                                    .map(gatewayResponse -> "New Account saved" + "\n" + cardResponse + "\n" + gatewayResponse);
                        })
                )
                .doOnError(err -> Mono.error(new RuntimeException("Error saving the account: " + err.getMessage())));
    }
}
