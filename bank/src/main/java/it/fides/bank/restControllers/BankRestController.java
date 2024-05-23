package it.fides.bank.restControllers;

import it.fides.bank.models.dtos.TestDto;
import it.fides.bank.models.entities.BankEntity;
import it.fides.bank.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/banks")
public class BankRestController {

    @Autowired
    private BankService bankService;

    @GetMapping("/healthcheck")
    public Mono<String> healthcheck() {
        return bankService.healthcheck();
    }

    @GetMapping("/all")
    public Flux<BankEntity> getAllBanks() {
        return bankService.getAllBanks();
    }

    @GetMapping("/{id}")
    public Mono<BankEntity> getBank(@PathVariable Long id) {
        return bankService.getBank(id);
    }

    @PostMapping
    public Mono<String> insertBank(@RequestBody BankEntity bank) {
        return bankService.insertBank(bank);
    }

    @PostMapping("/test")
    public Mono<TestDto> test(@RequestBody TestDto testDto) {
        testDto.setBankResponse("bank response ok");
        return Mono.just(testDto);
    }
}
