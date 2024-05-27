package it.fides.account.restControllers;

import it.fides.account.config.webClient.GatewayClient;
import it.fides.account.models.dtos.AccountDto;
import it.fides.account.models.dtos.JwtData;
import it.fides.account.models.entities.AccountEntity;
import it.fides.account.models.enums.RoleEnum;
import it.fides.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private GatewayClient gatewayClient;

    @GetMapping("/healthcheck")
    public Mono<String> healthcheck() {
        return accountService.healthcheck();
    }

    @GetMapping("/all")
    public Flux<AccountEntity> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Mono<AccountEntity> getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PostMapping
    public Mono<String> insertAccount(@RequestBody AccountDto accountDto, ServerWebExchange exchange) {
        JwtData jwt = accountService.extractJwtData(exchange);
        if (jwt.getRole().equals(RoleEnum.DEFAULT.getLabel())) {
            return accountService.insertAccount(accountDto, jwt.getId());
        }
        return Mono.error(new RuntimeException("Unhautorize endpoint called"));
    }
}
