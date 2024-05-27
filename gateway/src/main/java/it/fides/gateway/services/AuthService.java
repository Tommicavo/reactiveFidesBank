package it.fides.gateway.services;

import it.fides.gateway.models.dtos.*;
import it.fides.gateway.models.entities.BlackTokenEntity;
import it.fides.gateway.models.enums.RoleEnum;
import it.fides.gateway.models.repositories.BlackTokenRepository;
import it.fides.gateway.util.GatewayLogger;
import it.fides.gateway.config.webClient.GatewayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder bCrypt;

    @Autowired
    private GatewayClient gatewayClient;

    @Autowired
    private BlackTokenRepository blackTokenRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GatewayLogger gatewayLogger;

    public Mono<String> signin(SigninDto signinDto) {
        RoleDto role = new RoleDto();
        role.setIdRole(signinDto.isAdmin() ? RoleEnum.ADMIN.getId() : RoleEnum.DEFAULT.getId());
        role.setLabelRole(signinDto.isAdmin() ? RoleEnum.ADMIN.getLabel() : RoleEnum.DEFAULT.getLabel());

        UserDto user = new UserDto();
        user.setFirstNameUser(signinDto.getFirstName());
        user.setLastNameUser(signinDto.getLastName());
        user.setEmailUser(signinDto.getEmail());
        user.setPasswordUser(bCrypt.encode(signinDto.getPassword()));
        user.setRole(role);

        return gatewayClient.signin(user)
                .then(Mono.just("New User signed in"))
                .onErrorResume(err -> {
                    gatewayLogger.log.error("Error: " + err.getMessage());
                    return Mono.error(err);
                });
    }

    public Mono<String> login(LoginDto loginDto) {
        UserDto userDto = new UserDto();
        userDto.setEmailUser(loginDto.getEmail());

        return gatewayClient.getUserByEmail(userDto)
                .flatMap(user -> {
                    if (user != null && bCrypt.matches(loginDto.getPassword(), user.getPasswordUser())) {
                        String token = jwtService.generateToken(user);
                        return Mono.just(token);
                    } else {
                        return Mono.error(new RuntimeException("Invalid credentials"));
                    }
                })
                .onErrorResume(err -> {
                    gatewayLogger.log.error("Login error: " + err.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<String> logout(ServerWebExchange exchange) {
        String token = jwtService.getJwtFromRequest(exchange);
        BlackTokenEntity blackToken = new BlackTokenEntity(token);
        return blackTokenRepo.save(blackToken)
                .then(Mono.just("Your access token has been black listed"));
    }
}
