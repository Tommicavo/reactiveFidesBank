package it.fides.gateway.restControllers;

import it.fides.gateway.models.dtos.LoginDto;
import it.fides.gateway.models.dtos.SigninDto;
import it.fides.gateway.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public Mono<String> signin(@RequestBody SigninDto signinDto) {
        return authService.signin(signinDto);
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @GetMapping("/logout")
    public Mono<String> logout(ServerWebExchange exchange) {
        return authService.logout(exchange);
    }
}
